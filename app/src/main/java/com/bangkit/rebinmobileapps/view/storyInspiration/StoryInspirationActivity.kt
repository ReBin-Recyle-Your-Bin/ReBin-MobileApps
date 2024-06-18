package com.bangkit.rebinmobileapps.view.storyInspiration

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.adapter.StoryInpirationAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.databinding.ActivityStoryInspirationBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.main.MainViewModel
import kotlinx.coroutines.launch

class StoryInspirationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryInspirationBinding
    private lateinit var storyRecyclerView: RecyclerView
    private val storyInpirationAdapter = StoryInpirationAdapter()

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryInspirationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupAction()

        binding.tblStoryInspiration.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        storyRecyclerView = binding.rvStoryInspirationAll
        storyRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@StoryInspirationActivity)
            adapter = storyInpirationAdapter
        }
    }

    private fun setupAction() {
        lifecycleScope.launch {
            viewModel.getStoryInspiration().observe(this@StoryInspirationActivity) { story ->
                when (story) {
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        storyInpirationAdapter.submitList(story.data)
                        binding.rvStoryInspirationAll.adapter = storyInpirationAdapter
                    }
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this@StoryInspirationActivity, "Error: ${story.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}