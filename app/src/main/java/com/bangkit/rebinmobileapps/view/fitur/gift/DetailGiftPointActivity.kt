package com.bangkit.rebinmobileapps.view.fitur.gift

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.data.response.GiftPointItem
import com.bangkit.rebinmobileapps.databinding.ActivityDetailGiftPointBinding
import com.bumptech.glide.Glide

class DetailGiftPointActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGiftPointBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGiftPointBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val giftPoint = intent.getParcelableExtra<GiftPointItem>(EXTRA_GIFT_POINT) as GiftPointItem
        setupGiftPointDetail(giftPoint)

        binding.tblDetailGiftPoint.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupGiftPointDetail(giftPoint: GiftPointItem) {
        binding.tvTitleGiftPointDetail.text = giftPoint.title
        binding.tvPointGiftPointDetail.text = giftPoint.point
        binding.tvDetailDescriptionGiftPoint.text = giftPoint.description

        Glide.with(applicationContext)
            .load(giftPoint.photoUrl)
            .into(binding.ivDetailGiftPoint)
    }

    companion object {
        const val EXTRA_GIFT_POINT = "extra_gift_point"
    }
}