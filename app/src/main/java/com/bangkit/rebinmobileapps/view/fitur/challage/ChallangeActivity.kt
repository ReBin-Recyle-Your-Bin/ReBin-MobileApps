package com.bangkit.rebinmobileapps.view.fitur.challage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.databinding.ActivityChallangeBinding

class ChallangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChallangeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}