package com.bangkit.rebinmobileapps.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.data.model.UserModel
import com.bangkit.rebinmobileapps.databinding.ActivityLoginBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.customView.CustomTextEmail
import com.bangkit.rebinmobileapps.view.customView.CustomTextPassword
import com.bangkit.rebinmobileapps.view.main.MainActivity
import com.bangkit.rebinmobileapps.view.signup.SignUpActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var customTextEmail: CustomTextEmail
    private lateinit var customTextPassword: CustomTextPassword

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //untuk mensetup login
        setupAction()
        playAnimation()

        customTextEmail = binding.emailEdittext
        customTextPassword = binding.passwordEditText


        binding.tbSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLoginGoogle.setOnClickListener {
            Toast.makeText(this, "Fitur Masuk dengan Google akan segera hadir", Toast.LENGTH_SHORT).show()
        }

        binding.forgotPasswordTextView.setOnClickListener {
            Toast.makeText(this, "Fitur Lupa Password akan segera hadir", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEdittext.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            viewModel.login(email,password).observe(this){user->
                when(user){
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        saveSession(
                            UserModel(
                                user.data.data.userId,
                                user.data.data.token,
                                user.data.data.name,
                                true
                            )
                        )
                        Toast.makeText(this, user.data.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResultState.Loading ->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Error ->{
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this, user.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveSession(session: UserModel){
        lifecycleScope.launch {
            viewModel.saveSession(session)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ViewModelFactory.clearInstance()
            startActivity(intent)
        }
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
        val signUpTextView = ObjectAnimator.ofFloat(binding.tbSignUp, View.ALPHA, 1f).setDuration(100)

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











