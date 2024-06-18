package com.bangkit.rebinmobileapps.view.fitur.challage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.rebinmobileapps.databinding.ActivityDetailChallangeBinding

class DetailChallangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailChallangeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailChallangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}