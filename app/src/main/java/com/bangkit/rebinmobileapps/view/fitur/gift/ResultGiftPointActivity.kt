package com.bangkit.rebinmobileapps.view.fitur.gift

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.databinding.ActivityResultGiftPointBinding

class ResultGiftPointActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultGiftPointBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultGiftPointBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}