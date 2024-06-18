package com.bangkit.rebinmobileapps.view.fitur.challage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.data.response.ChallengeItem
import com.bangkit.rebinmobileapps.databinding.ActivityDetailChallangeBinding
import com.bumptech.glide.Glide

class DetailChallangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailChallangeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailChallangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val challange = intent.getParcelableExtra<ChallengeItem>(EXTRA_CHALLANGE) as ChallengeItem
        setupChallangeDetail(challange)

        binding.tblDetailChallange.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupChallangeDetail(challange: ChallengeItem) {
        binding.tvTitleChallangeDetail.text = challange.title
        binding.tvPointChallageDetail.text = challange.point
        binding.tvDetailDescriptionChallange.text = challange.description

        Glide.with(applicationContext)
            .load(challange.photoUrl)
            .into(binding.ivDetailChallange)
    }

    companion object {
        const val EXTRA_CHALLANGE = "extra_challange"
    }
}