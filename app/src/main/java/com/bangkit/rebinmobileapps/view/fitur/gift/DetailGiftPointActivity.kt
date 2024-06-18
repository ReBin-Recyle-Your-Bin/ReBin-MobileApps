package com.bangkit.rebinmobileapps.view.fitur.gift

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.databinding.ActivityDetailGiftPointBinding

class DetailGiftPointActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGiftPointBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGiftPointBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}