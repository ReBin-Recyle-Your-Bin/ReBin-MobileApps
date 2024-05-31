package com.bangkit.rebinmobileapps.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.databinding.ActivityMainBinding
import com.bangkit.rebinmobileapps.view.detection.DetectionActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        binding.fabBtnDetection.setOnClickListener {
            intent = Intent(this, DetectionActivity::class.java)
            startActivity(intent)
        }
    }


}