package com.bangkit.rebinmobileapps.view.detection

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.response.DetectionResult
import com.bangkit.rebinmobileapps.data.response.Recommendation
import com.bangkit.rebinmobileapps.databinding.ActivityResultDetectionBinding
import com.bangkit.rebinmobileapps.view.main.MainActivity

class ResultDetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDetectionBinding
    private lateinit var tvResultWashType: TextView
    private lateinit var tvResultConfidence: TextView
    private lateinit var tvRecomendation: TextView
    private lateinit var tvRecomendationContent: TextView
    private lateinit var llRecommendations : LinearLayout

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
//        tvRecomendationContent = binding.tvRecomendationContent
        llRecommendations = binding.llRecommendations


        val detectionResult: DetectionResult? = intent.getParcelableExtra("detectionResult")

        detectionResult?.let {
            tvResultWashType.text = it.label ?: "Unknown"
            tvResultConfidence.text = "${it.accuracy} %" ?: "Unknown"
            tvRecomendation.text = "Recommendation:"
            if (it.recommendation.isNotEmpty()) {
                val recommendationsToShow = it.recommendation.take(3)
                for(recomendation in recommendationsToShow){
                    addRecomendationView(recomendation)
                }
            } else {
                val noRecommendation = TextView(this)
                noRecommendation.text = "No recommendation available"
                llRecommendations.addView(noRecommendation)
            }

        } ?: showToast("No detection result found")
    }

    private fun addRecomendationView(recommendation: Recommendation) {
        val recomendationView: View = LayoutInflater.from(this).inflate(R.layout.item_recomendation, llRecommendations, false)
        val tvName: TextView = recomendationView.findViewById(R.id.tv_title_recommendation)
        val tvClass: TextView = recomendationView.findViewById(R.id.tv_class_recommendation)

        tvName.text = "Name: ${recommendation.name}"
        tvClass.text = "Class: ${recommendation.Class}"

        llRecommendations.addView(recomendationView)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}