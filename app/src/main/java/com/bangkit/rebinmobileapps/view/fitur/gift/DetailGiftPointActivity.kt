package com.bangkit.rebinmobileapps.view.fitur.gift

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.data.api.ApiConfig
import com.bangkit.rebinmobileapps.data.response.GiftPointItem
import com.bangkit.rebinmobileapps.databinding.ActivityDetailGiftPointBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.main.MainViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailGiftPointActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGiftPointBinding
    private val viewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }
    private var userPoints: Int = 0
    private var requiredPoints: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGiftPointBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPoint()
        val giftPoint = intent.getParcelableExtra<GiftPointItem>(EXTRA_GIFT_POINT) as GiftPointItem
        setupGiftPointDetail(giftPoint)



        binding.tblDetailGiftPoint.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnPointGift.setOnClickListener {
            if (userPoints >= requiredPoints) {
                viewModel.getSession().observe(this) { session ->
                    val userId = session.userId
                    val token = session.token

                    exitPointUser(giftPoint.point, userId, token)
                }
                Toast.makeText(this, "Berhasil menukar point", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Point anda tidak cukup", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupGiftPointDetail(giftPoint: GiftPointItem) {
        binding.tvTitleGiftPointDetail.text = giftPoint.title
        binding.tvPointNeeded.text = giftPoint.point
        binding.tvDetailDescriptionGiftPoint.text = giftPoint.description
        requiredPoints = giftPoint.point.toIntOrNull() ?: 0

        Glide.with(applicationContext)
            .load(giftPoint.photoUrl)
            .into(binding.ivDetailGiftPoint)

    }

    private fun setupPoint() {
        viewModel.getPointHistory().observe(this) { pointHistory ->
            when (pointHistory) {
                is ResultState.Success -> {
                    val totalPoints = pointHistory.data
                        .filter { it.status == "entry" }
                        .sumBy { it.point.toIntOrNull() ?: 0 }
                    userPoints = totalPoints
                    binding.tvPointYours.text = userPoints.toString()
                }

                is ResultState.Loading -> {
                    // Handle loading state if needed
                }

                is ResultState.Error -> {
                    // Handle error state if needed
                }
            }
        }
    }

    private fun exitPointUser(pointExit: String,userId: String, token: String){
        val requestBody = mapOf(
            "userId" to userId,
            "description" to "Tukar point untuk hadiah",
            "point" to pointExit,
            "status" to "exit"
        )
        val apiService = ApiConfig.getDataApiService(token)
        val call = apiService.postPoint(requestBody)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Point berhasil ditukarkan")
                } else {
                    showToast("Gagal menukarkan point")
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
        const val EXTRA_GIFT_POINT = "extra_gift_point"
    }
}
