package com.bangkit.rebinmobileapps.view.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.data.UserPreferences
import com.bangkit.rebinmobileapps.data.api.ApiConfig
import com.bangkit.rebinmobileapps.data.dataStore
import com.bangkit.rebinmobileapps.data.response.DetectionResult
import com.bangkit.rebinmobileapps.data.response.ErrorResponse
import com.bangkit.rebinmobileapps.databinding.FragmentProfileBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.customView.CustomTextEmail
import com.bangkit.rebinmobileapps.view.customView.CustomTextPassword
import com.bangkit.rebinmobileapps.view.main.MainActivity
import com.bangkit.rebinmobileapps.view.main.MainViewModel
import com.bangkit.rebinmobileapps.view.welcome.WelcomeActivity
import com.google.android.material.snackbar.Snackbar
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var customTextEmail: CustomTextEmail
    private lateinit var customTextPassword: CustomTextPassword

    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        customTextEmail = binding.emailEdittext
        customTextPassword = binding.passwordEditText

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup toolbar
        val toolbar: Toolbar = binding.tlbProfile
        toolbar.title = getString(R.string.profile)
        toolbar.inflateMenu(R.menu.menu_profile)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    performLogout()
                    Snackbar.make(view, "Logout clicked", Snackbar.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Setup image selection
        binding.ivProfile.setOnClickListener { startGallery() }

        setupAction()
        setupUpdateProfile()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == AppCompatActivity.RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                binding.ivProfile.setImageURI(resultUri)
                croppedImageUri = resultUri
            } ?: showToast("Error crop image")
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            showToast("Crop error: ${cropError?.message}")
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherGallery.launch(chooser)
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data
            selectedImg?.let { uri ->
                currentImageUri = uri
                showImage()
                startUCrop(uri)
            } ?: showToast("Error image selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivProfile.setImageURI(it)
        }
    }

    private fun startUCrop(sourceUri: Uri) {
        val fileName = "cropped_image_${System.currentTimeMillis()}.jpg"
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, fileName))
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1000, 1000)
            .start(requireContext(), this)
    }

    private fun performLogout() {
        lifecycleScope.launch {
            viewModel.logout()
            val intent = Intent(activity, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun setupAction() {
        lifecycleScope.launch {
            viewModel.getProfile().observe(viewLifecycleOwner, Observer { resultState ->
                when (resultState) {
                    is ResultState.Loading -> {
                        // Show loading indicator
                    }
                    is ResultState.Success -> {
                        val profile = resultState.data// Mengakses elemen pertama dari list
                        binding.nameEditText.setText(profile.name)
                        binding.emailEdittext.setText(profile.email)
                    }
                    is ResultState.Error -> {
                        val errorMessage = resultState.error
                        Toast.makeText(requireContext(), resultState.error, Toast.LENGTH_SHORT).show()
                        Log.e("ProfileFragment", errorMessage)
                    }
                }
            })
        }
    }

    private fun setupUpdateProfile() {
        binding.updateButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEdittext.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            lifecycleScope.launch {
                val token = getToken(requireContext())
                val userID = getUserID(requireContext())
                if (token != null && userID != null) {
                    croppedImageUri?.let { uri ->
                        showLoading(true)
                        uploadProfilePhoto(uri, userID, token)
                    }
                }

                viewModel.updateProfile(name, email, password).observe(viewLifecycleOwner, Observer { user ->
                    when (user) {
                        is ResultState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ResultState.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE

                            Toast.makeText(requireContext(), user.data.message, Toast.LENGTH_SHORT).show()
                            //Berpindah ke Main Activity (HomeFragment)
                            val intent = Intent(activity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            activity?.finish()
                        }

                        is ResultState.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(requireContext(), user.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    private fun uploadProfilePhoto(uri: Uri, userID: String, token: String) {
        val file = File(uri.path!!)
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val photoPart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val userIDPart = RequestBody.create("text/plain".toMediaTypeOrNull(), userID)

        val apiService = ApiConfig.getDataApiService(token)
        val call = apiService.uploadProfilePhoto(photoPart, userIDPart)
        call.enqueue(object : Callback<ErrorResponse> {
            override fun onResponse(call: Call<ErrorResponse>, response: Response<ErrorResponse>) {
                if (response.isSuccessful) {
                    val uploadResponse = response.body()
                    uploadResponse?.let {
                        // Menggunakan requireContext() atau context yang valid dari fragment
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Menggunakan requireContext() atau context yang valid dari fragment
                    Toast.makeText(requireContext(), "Gagal upload foto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                // Menggunakan requireContext() atau context yang valid dari fragment
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                performLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private suspend fun getUserID(context: Context): String? {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val user = userPreferences.getSession().first()
        return user.userId
    }

    private suspend fun getToken(context: Context): String? {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val user = userPreferences.getSession().first()
        return user.token
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        binding.updateButton.isEnabled = isLoading
    }
}
