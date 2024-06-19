package com.bangkit.rebinmobileapps.view.detail

import android.os.Bundle
import android.text.Html
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
        //binding.tvIngredientDetailCraft.text = detailCraft.ingredients
        //binding.tvStepDetailCraft.text = detailCraft.steps

        // Format bahan-bahan
        val formattedIngredients = formatAsNumberedList(detailCraft.ingredients)
        binding.tvIngredientDetailCraft.text = Html.fromHtml(formattedIngredients, Html.FROM_HTML_MODE_COMPACT)

        // Format langkah-langkah
        val formattedSteps = formatAsNumberedList(detailCraft.steps)
        binding.tvStepDetailCraft.text = Html.fromHtml(formattedSteps, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(applicationContext)
            .load(detailCraft.photoUrl)
            .into(binding.ivDetailCraft)
    }

    private fun setupRecommendationDetail(recommendation: Recommendation) {
        binding.tvTitleDetailCraft.text = recommendation.name
        binding.tvLabelDetailCraft.text = recommendation.classItem
        binding.tvDescriptionDetailCraft.text = recommendation.description
        //binding.tvIngredientDetailCraft.text = recommendation.ingredients
        //binding.tvStepDetailCraft.text = recommendation.steps

        // Format bahan-bahan
        val formattedIngredients = formatAsNumberedList(recommendation.ingredients)
        binding.tvIngredientDetailCraft.text = Html.fromHtml(formattedIngredients, Html.FROM_HTML_MODE_COMPACT)

        // Format langkah-langkah
        val formattedSteps = formatAsNumberedList(recommendation.steps)
        binding.tvStepDetailCraft.text = Html.fromHtml(formattedSteps, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(this)
            .load(recommendation.pics_url)
            .placeholder(R.drawable.ic_place_holder)
            .into(binding.ivDetailCraft)
    }

    // Fungsi untuk memformat teks sebagai daftar bernomor
    private fun formatAsNumberedList(text: String): String {
        val items = text.split("\n")  // Asumsikan setiap item dipisahkan dengan newline
        val stringBuilder = StringBuilder("<ol>")
        for (item in items) {
            stringBuilder.append("<li>").append(item).append("</li>")
        }
        stringBuilder.append("</ol>")
        return stringBuilder.toString()
    }

    companion object {
        const val DETAIL_CRAFT = "detail_craft"
        const val DETAIL_CRAFT_RECOMMENDATION = "detail_craft_recommendation"
    }
}
