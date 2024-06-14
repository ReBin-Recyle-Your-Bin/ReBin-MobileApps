package com.bangkit.rebinmobileapps.view.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.response.StoryItem
import com.bangkit.rebinmobileapps.databinding.ActivityDetailStoryBinding
import com.bangkit.rebinmobileapps.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailStory = intent.getParcelableExtra<StoryItem>(DETAIL_STORY_INSPIRATION) as StoryItem
        setupAction(detailStory)

        supportActionBar?.show()
        supportActionBar?.title = "Detail Story"

    }

    private fun setupAction(detailStory: StoryItem){
        Glide.with(applicationContext)
            .load(detailStory.photoUrl)
            .into(binding.ivDetailCraft)

        binding.tvDetailTitle.text = detailStory.title
        binding.tvDetailAuthor.text = detailStory.author
        binding.tvDetailDescription.text = detailStory.description


    }

    companion object {
        const val DETAIL_STORY_INSPIRATION = "detail_story_inspiration"
    }
}