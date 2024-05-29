package com.bangkit.rebinmobileapps.view.login

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.databinding.ActivityLoginBinding
import com.bangkit.rebinmobileapps.view.customView.CustomTextEmail
import com.bangkit.rebinmobileapps.view.customView.CustomTextPassword

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var customTextEmail: CustomTextEmail
    private lateinit var customTextPassword: CustomTextPassword
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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











