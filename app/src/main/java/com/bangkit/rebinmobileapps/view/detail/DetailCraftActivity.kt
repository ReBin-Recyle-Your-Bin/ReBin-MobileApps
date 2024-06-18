package com.bangkit.rebinmobileapps.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.response.Recommendation
import com.bangkit.rebinmobileapps.data.response.SearchCraftItems
import com.bangkit.rebinmobileapps.databinding.ActivityDetailCraftBinding
import com.bumptech.glide.Glide

class DetailCraftActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCraftBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCraftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailCraft = intent.getParcelableExtra<SearchCraftItems>(DETAIL_CRAFT)
        val recommendation = intent.getParcelableExtra<Recommendation>(DETAIL_CRAFT_RECOMMENDATION)

        if (detailCraft != null) {
            setupCraftDetail(detailCraft)
        } else if (recommendation != null) {
            setupRecommendationDetail(recommendation)
        }

        binding.tblDetailCraft.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupCraftDetail(detailCraft: SearchCraftItems) {
        binding.tvTitleDetailCraft.text = detailCraft.name
        val formattedClassName = detailCraft.className.replace("-", " ")
        binding.tvLabelDetailCraft.text = formattedClassName
        binding.tvDescriptionDetailCraft.text = detailCraft.description
        binding.tvIngredientDetailCraft.text = detailCraft.ingredients
        binding.tvStepDetailCraft.text = detailCraft.steps

        Glide.with(applicationContext)
            .load(detailCraft.photoUrl)
            .into(binding.ivDetailCraft)
    }

    private fun setupRecommendationDetail(recommendation: Recommendation) {
        binding.tvTitleDetailCraft.text = recommendation.name
        binding.tvLabelDetailCraft.text = recommendation.classItem
        binding.tvDescriptionDetailCraft.text = recommendation.description
        binding.tvIngredientDetailCraft.text = recommendation.ingredients
        binding.tvStepDetailCraft.text = recommendation.steps

        Glide.with(this)
            .load(recommendation.pics_url)
            .placeholder(R.drawable.ic_place_holder)
            .into(binding.ivDetailCraft)
    }

    companion object {
        const val DETAIL_CRAFT = "detail_craft"
        const val DETAIL_CRAFT_RECOMMENDATION = "detail_craft_recommendation"
    }
}
