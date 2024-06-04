package com.bangkit.rebinmobileapps.view.detection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.databinding.ActivityResultDetectionBinding
import com.bangkit.rebinmobileapps.view.main.MainActivity

class ResultDetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDetectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tlbResultDetection.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}