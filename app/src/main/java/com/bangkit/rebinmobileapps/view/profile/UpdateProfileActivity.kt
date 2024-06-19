package com.bangkit.rebinmobileapps.view.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.data.UserPreferences
import com.bangkit.rebinmobileapps.data.api.ApiConfig
import com.bangkit.rebinmobileapps.data.dataStore
import com.bangkit.rebinmobileapps.data.response.ErrorResponse
import com.bangkit.rebinmobileapps.databinding.ActivityUpdateProfileBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.customView.CustomTextPassword
import com.bangkit.rebinmobileapps.view.main.MainActivity
import com.bangkit.rebinmobileapps.view.main.MainViewModel
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

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var customTextPassword: CustomTextPassword

    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customTextPassword = binding.passwordEditText

        binding.tlbUpdateProfile.setNavigationOnClickListener {
            onBackPressed()
        }

        // Setup image selection
//        binding.ivProfile.setOnClickListener { startGallery() }

        setupAction()
        setupUpdateProfile()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
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

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data
            selectedImg?.let { uri ->
                currentImageUri = uri
                showImage()
                startUCrop(uri)
            } ?: showToast("Error image selected")
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherGallery.launch(chooser)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivProfile.setImageURI(it)
        }
    }

    private fun startUCrop(sourceUri: Uri) {
        val fileName = "cropped_image_${System.currentTimeMillis()}.jpg"
        val destinationUri = Uri.fromFile(File(cacheDir, fileName))
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1000, 1000)
            .start(this)
    }

    private fun setupAction() {
        lifecycleScope.launch {
            viewModel.getProfile().observe(this@UpdateProfileActivity, Observer { resultState ->
                when (resultState) {
                    is ResultState.Loading -> {
                        // Show loading indicator
                    }
                    is ResultState.Success -> {
                        val profile = resultState.data
                        binding.nameEditText.setText(profile.name)
                        binding.emailEdittext.setText(profile.email)
                    }
                    is ResultState.Error -> {
                        val errorMessage = resultState.error
                        Toast.makeText(this@UpdateProfileActivity, resultState.error, Toast.LENGTH_SHORT).show()
                        Log.e("ProfileActivity", errorMessage)
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
                val token = getToken(applicationContext)
                val userID = getUserID(applicationContext)
                if (token != null && userID != null) {
                    croppedImageUri?.let { uri ->
                        showLoading(true)
                        uploadProfilePhoto(uri, userID, token)
                    }
                }

                viewModel.updateProfile(name, email, password).observe(this@UpdateProfileActivity, Observer { user ->
                    when (user) {
                        is ResultState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ResultState.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE

                            Toast.makeText(this@UpdateProfileActivity, user.data.message, Toast.LENGTH_SHORT).show()
                            //Berpindah ke Main Activity (HomeFragment)
                            val intent = Intent(this@UpdateProfileActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }

                        is ResultState.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this@UpdateProfileActivity, user.error, Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(this@UpdateProfileActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@UpdateProfileActivity, "Gagal upload foto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ErrorResponse>, t: Throwable) {
                Toast.makeText(this@UpdateProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private suspend fun getUserID(context: Context): String {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val user = userPreferences.getSession().first()
        return user.userId
    }

    private suspend fun getToken(context: Context): String {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val user = userPreferences.getSession().first()
        return user.token
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.updateButton.isEnabled = !isLoading
    }
}










