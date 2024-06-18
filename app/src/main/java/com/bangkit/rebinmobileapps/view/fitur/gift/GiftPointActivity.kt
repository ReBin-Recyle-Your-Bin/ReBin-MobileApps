package com.bangkit.rebinmobileapps.view.fitur.gift

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.databinding.ActivityGiftPointBinding

class GiftPointActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGiftPointBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGiftPointBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}