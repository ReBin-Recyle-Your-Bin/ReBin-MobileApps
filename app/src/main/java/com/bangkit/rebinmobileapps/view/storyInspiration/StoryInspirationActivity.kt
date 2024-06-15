package com.bangkit.rebinmobileapps.view.storyInspiration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.databinding.ActivityStoryInspirationBinding

class StoryInspirationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryInspirationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryInspirationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}