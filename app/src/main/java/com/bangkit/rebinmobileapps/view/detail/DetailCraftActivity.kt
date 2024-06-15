package com.bangkit.rebinmobileapps.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.data.response.SearchCraftItems
import com.bangkit.rebinmobileapps.databinding.ActivityDetailCraftBinding

class DetailCraftActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCraftBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCraftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailCraft = intent.getParcelableExtra<SearchCraftItems>(DETAIL_CRAFT) as SearchCraftItems
        setupAction(detailCraft)

        binding.tblDetailCraft.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupAction(detailCraft: SearchCraftItems){
        binding.tvTitleDetailCraft.text = detailCraft.name
        binding.tvLabelDetailCraft.text = detailCraft.className
        binding.tvDescriptionDetailCraft.text = detailCraft.description
    }

    companion object {
        const val DETAIL_CRAFT = "detail_craft"
    }
}