package com.bangkit.rebinmobileapps.view.search

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.databinding.ActivityCraftByCategoryBinding

class CraftByCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCraftByCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCraftByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}