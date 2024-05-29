package com.bangkit.rebinmobileapps.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
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

        playAnimation()

        customTextEmail = binding.emailEdittext
        customTextPassword = binding.passwordEditText
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.loginTitleTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val forgot = ObjectAnimator.ofFloat(binding.forgotPasswordTextView, View.ALPHA, 1f).setDuration(100)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
        val or = ObjectAnimator.ofFloat(binding.orTextView, View.ALPHA, 1f).setDuration(100)
        val btnLoginGoogle = ObjectAnimator.ofFloat(binding.buttonLoginGoogle, View.ALPHA, 1f).setDuration(100)
        val dontAccountTextView = ObjectAnimator.ofFloat(binding.tvDontHaveAccount, View.ALPHA, 1f).setDuration(100)
        val signUpTextView = ObjectAnimator.ofFloat(binding.tvSignUp, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                forgot,
                btnLogin,
                or,
                btnLoginGoogle,
                dontAccountTextView,
                signUpTextView
            )
            startDelay = 100
        }.start()
    }



}











