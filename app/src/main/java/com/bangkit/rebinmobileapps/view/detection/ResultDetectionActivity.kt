package com.bangkit.rebinmobileapps.view.detection

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.api.ApiConfig
import com.bangkit.rebinmobileapps.data.response.DetectionResult
import com.bangkit.rebinmobileapps.data.response.Recommendation
import com.bangkit.rebinmobileapps.databinding.ActivityResultDetectionBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.detail.DetailCraftActivity
import com.bangkit.rebinmobileapps.view.detail.DetailCraftActivity.Companion.DETAIL_CRAFT_RECOMMENDATION
import com.bangkit.rebinmobileapps.view.main.MainActivity
import com.bangkit.rebinmobileapps.view.main.MainViewModel
import com.bangkit.rebinmobileapps.view.search.SearchFragment
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultDetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDetectionBinding
    private lateinit var tvResultWashType: TextView
    private lateinit var tvResultConfidence: TextView
    private lateinit var tvRecomendation: TextView
    private lateinit var llRecommendations : LinearLayout
    private lateinit var ivResultDetection : ImageView

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tlbResultDetection.setNavigationOnClickListener {
            startActivity(Intent(this, DetectionActivity::class.java))
        }

        binding.seeMoreButton.setOnClickListener {
            //startActivity(Intent(this, MainActivity::class.java))
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("openSearchFragment", true)
            startActivity(intent)
        }

        binding.btnToHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        tvResultWashType = binding.tvResultWashType
        tvResultConfidence = binding.tvResultConfidence
        tvRecomendation = binding.tvRecomendation
        llRecommendations = binding.llRecommendations
        ivResultDetection = binding.ivResultDetection

        viewModel.getSession().observe(this) { session ->
            val userId = session.userId
            val token = session.token

            val detectionResult: DetectionResult? = intent.getParcelableExtra(DETECTION_RESULT)
            val imageUriString: String? = intent.getStringExtra(IMAGE_URI)

            detectionResult?.let {
                tvResultWashType.text = it.label ?: "Unknown"
                tvResultConfidence.text = "${it.accuracy} %" ?: "Unknown"
                tvRecomendation.text = "Rekomendasi :"
                if (it.recommendation.isNotEmpty()) {
                    val recommendationsToShow = it.recommendation.take(3)
                    for (recommendation in recommendationsToShow) {
                        addRecomendationView(recommendation)
                    }
                } else {
                    val noRecommendation = TextView(this)
                    noRecommendation.text = "Tidak ada rekomendasi yang tersedia"
                    llRecommendations.addView(noRecommendation)
                }
                showToast("Detecksi Berhasil")
                sendDetectionResultToHistory(detectionResult, userId, token)
                sendPointToUser(userId, token)
            } ?: showToast("Tidak ditemukan hasil deteksi")

            imageUriString?.let {
                val imageUri = Uri.parse(it)
                Glide.with(this)
                    .load(imageUri)
                    .placeholder(R.drawable.ic_place_holder)
                    .into(ivResultDetection)
            }
        }
    }

    private fun addRecomendationView(recommendation: Recommendation) {
        val recomendationView: View = LayoutInflater.from(this).inflate(R.layout.item_recomendation, llRecommendations, false)
        val tvName: TextView = recomendationView.findViewById(R.id.tv_title_recommendation)
        val tvClass: TextView = recomendationView.findViewById(R.id.tv_class_recommendation)
        val tvDesc: TextView = recomendationView.findViewById(R.id.tv_desc_recomendation)
        val ivRecommendation: ImageView = recomendationView.findViewById(R.id.iv_image_recomendation)

        tvName.text = recommendation.name
        tvClass.text = recommendation.classItem
        tvDesc.text = recommendation.description

        val imageUrl = recommendation.pics_url.trim()
        if (imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_place_holder)
                .into(ivRecommendation)
        } else {
            ivRecommendation.setImageResource(R.drawable.ic_place_holder)
        }

        recomendationView.setOnClickListener {
            val intent = Intent(this, DetailCraftActivity::class.java).apply {
                putExtra(DETAIL_CRAFT_RECOMMENDATION, recommendation)
            }
            startActivity(intent)
        }

        Log.d("Recommendation", "Recommendation: ${recommendation}")
        llRecommendations.addView(recomendationView)

    }

    private fun sendDetectionResultToHistory(detectionResult: DetectionResult?, userId: String, token: String){
        val requestBody = mapOf(
            "userId" to userId,
            "accuracy" to (detectionResult?.accuracy ?: "0%"),
            "label" to (detectionResult?.label ?: "Unknown")
        )

        val apiService = ApiConfig.getDataApiService(token)
        val call = apiService.sendDetectionResulToHistory(requestBody)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Hasil deteksi berhasil dikirim ke riwayat")
                } else {
                    showToast("Gagal mengirimkan hasil deteksi ke riwayat")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun sendPointToUser(userId: String, token: String){
        val requestBody = mapOf(
            "userId" to userId,
            "description" to "Mendapatkan point dari check in hasil deteksi sampah",
            "point" to "100",
            "status" to "entry"
        )
        val apiService = ApiConfig.getDataApiService(token)
        val call = apiService.postPoint(requestBody)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Point berhasil ditambahkan")
                } else {
                    showToast("Gagal menambahkan point")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Error: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val DETECTION_RESULT = "detectionResult"
        const val IMAGE_URI = "imageUri"
    }
}