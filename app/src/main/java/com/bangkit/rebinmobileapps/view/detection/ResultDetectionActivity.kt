package com.bangkit.rebinmobileapps.view.detection

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.data.response.DetectionResult
import com.bangkit.rebinmobileapps.databinding.ActivityResultDetectionBinding
import com.bangkit.rebinmobileapps.view.main.MainActivity

class ResultDetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDetectionBinding
    private lateinit var tvResultWashType: TextView
    private lateinit var tvResultConfidence: TextView
    private lateinit var tvRecomendation: TextView
    private lateinit var tvRecomendationContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tlbResultDetection.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        tvResultWashType = binding.tvResultWashType
        tvResultConfidence = binding.tvResultConfidence
        tvRecomendation = binding.tvRecomendation
        tvRecomendationContent = binding.tvRecomendationContent


        val detectionResult: DetectionResult? = intent.getParcelableExtra("detectionResult")

        detectionResult?.let {
            tvResultWashType.text = it.label ?: "Unknown"
            tvResultConfidence.text = "${it.accuracy} %" ?: "Unknown"
            tvRecomendation.text = "Recommendation:"
            if (it.recommendation.isNotEmpty()) {
                val recommendation = it.recommendation[0]
                tvRecomendationContent.text = "Name: ${recommendation.name}\n" +
                        "Class: ${recommendation.Class}\n" +
                        "Ingredients: ${recommendation.ingredients}\n" +
                        "Steps: ${recommendation.steps}"
            } else {
                tvRecomendationContent.text = "No recommendation available"
            }

        } ?: showToast("No detection result found")

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}