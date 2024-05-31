package com.bangkit.rebinmobileapps.view.detection

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.databinding.ActivityDetectionBinding

class DetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}