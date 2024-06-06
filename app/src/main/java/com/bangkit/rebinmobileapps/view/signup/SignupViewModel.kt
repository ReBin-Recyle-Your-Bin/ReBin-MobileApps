package com.bangkit.rebinmobileapps.view.signup

import androidx.lifecycle.ViewModel
import com.bangkit.rebinmobileapps.data.UserRepository

class SignupViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(
        name: String,
        email: String,
        password: String
    ) = repository.register(name, email, password)
}