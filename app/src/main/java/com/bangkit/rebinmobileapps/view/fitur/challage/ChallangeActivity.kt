package com.bangkit.rebinmobileapps.view.fitur.challage

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.rebinmobileapps.adapter.ChallengeAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.databinding.ActivityChallangeBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.main.MainViewModel

class ChallangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChallangeBinding
    private val challengeAdapter = ChallengeAdapter()

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupAction()

        binding.tlbChallange.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        val challengeRV = binding.rvChallenge
        challengeRV.apply {
            layoutManager = LinearLayoutManager(this@ChallangeActivity)
            adapter = challengeAdapter
        }
    }

    private fun setupAction() {
        // Setup action
        viewModel.getChallenge().observe(this@ChallangeActivity) { challenge ->
            when (challenge) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tvChallengeEmpty.visibility = View.GONE
                    challengeAdapter.submitList(challenge.data)
                    binding.rvChallenge.adapter = challengeAdapter
                }
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tvChallengeEmpty.visibility = View.VISIBLE
                }
            }
        }
    }
}