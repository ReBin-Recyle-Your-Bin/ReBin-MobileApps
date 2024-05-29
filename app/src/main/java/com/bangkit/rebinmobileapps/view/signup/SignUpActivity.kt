package com.bangkit.rebinmobileapps.view.signup

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.databinding.ActivitySignUpBinding
import com.bangkit.rebinmobileapps.view.customView.CustomTextEmail
import com.bangkit.rebinmobileapps.view.customView.CustomTextPassword

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var customTextEmail: CustomTextEmail
    private lateinit var customTextPassword: CustomTextPassword
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        customTextEmail = binding.emailEdittext
        customTextPassword = binding.passwordEditText
    }
}