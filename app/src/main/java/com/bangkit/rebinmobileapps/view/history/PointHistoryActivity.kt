package com.bangkit.rebinmobileapps.view.history

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.databinding.ActivityPointHistoryBinding

class PointHistoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPointHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tlbHistoryPoint.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}