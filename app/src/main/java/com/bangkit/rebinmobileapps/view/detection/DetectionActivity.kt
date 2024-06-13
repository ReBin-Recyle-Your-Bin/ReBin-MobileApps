package com.bangkit.rebinmobileapps.view.detection

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bangkit.rebinmobileapps.data.UserPreferences
import com.bangkit.rebinmobileapps.data.api.ApiConfig
import com.bangkit.rebinmobileapps.data.dataStore
import com.bangkit.rebinmobileapps.data.response.DetectionResult
import com.bangkit.rebinmobileapps.databinding.ActivityDetectionBinding
import com.bangkit.rebinmobileapps.getImageUri
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.main.MainViewModel
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import java.io.File

class DetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectionBinding

    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.tlbDetection.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.analizeButton.setOnClickListener {
            croppedImageUri?.let { uri ->
                lifecycleScope.launch {
                    val token = getToken(this@DetectionActivity)
                    if (token != null) {
                        showLoading(true)
                        uploadPhoto(uri, token)
                    } else {
                        showToast("No token found, please login first")
                    }
                }
            } ?: showToast("No image to analyze")
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                binding.previewImageView.setImageURI(resultUri)
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

    private fun startCamera(){
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
            startUCrop(currentImageUri!!)
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startUCrop(sourceUri: Uri) {
        val fileName = "cropped_image_${System.currentTimeMillis()}.jpg"
        val destinationUri = Uri.fromFile(File(cacheDir, fileName))
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1000, 1000)
            .start(this)
    }

    private fun uploadPhoto(uri: Uri, token: String) {
        val file = File(uri.path!!)
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val photoPart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val apiService = ApiConfig.getDetectionApiService(token)
        val call = apiService.uploadPhotoDetection(photoPart)
        call.enqueue(object : Callback<DetectionResult> {
            override fun onResponse(call: Call<DetectionResult>, response: Response<DetectionResult>) {
                showLoading(true)
                if (response.isSuccessful) {
                    val detectionResult = response.body()
                    detectionResult?.let {
                        // Tangani hasil deteksi di sini
                        val intent = Intent(this@DetectionActivity, ResultDetectionActivity::class.java)
                        intent.putExtra("detectionResult", detectionResult)
                        intent.putExtra("imageUri", uri.toString())
                        startActivity(intent)
                    }
                } else {
                    showToast("Failed to get detection result")
                }
            }

            override fun onFailure(call: Call<DetectionResult>, t: Throwable) {
                showLoading(false)
                showToast("Error: ${t.message}")
            }
        })
    }

    private suspend fun getToken(context: Context): String? {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val user = userPreferences.getSession().first()
        return user.token
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        binding.analizeButton.isEnabled = isLoading
    }


    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}