package com.bangkit.rebinmobileapps.view.fitur.gift

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.rebinmobileapps.adapter.GiftPointAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.databinding.ActivityGiftPointBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.main.MainViewModel

class GiftPointActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGiftPointBinding

    private val giftPointAdapter = GiftPointAdapter()

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGiftPointBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupAction()

        binding.tlbGiftPoint.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        val giftPointRV = binding.rvGiftPoint
        giftPointRV.apply {
            layoutManager = LinearLayoutManager(this@GiftPointActivity)
            adapter = giftPointAdapter
        }
    }

    private fun setupAction() {
        viewModel.getGiftPoint().observe(this@GiftPointActivity) { giftPoint ->
            when (giftPoint) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tvGiftPointEmpty.visibility = View.GONE
                    giftPointAdapter.submitList(giftPoint.data)
                    binding.rvGiftPoint.adapter = giftPointAdapter
                }
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tvGiftPointEmpty.visibility = View.VISIBLE
                }
            }
        }
    }
}