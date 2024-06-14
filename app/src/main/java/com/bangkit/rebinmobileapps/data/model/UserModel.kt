package com.bangkit.rebinmobileapps.data.model

data class UserModel(
    val userId: String,
    val token: String,
    val email: String,
    val password: String,
    val isLogin: Boolean = false
)
