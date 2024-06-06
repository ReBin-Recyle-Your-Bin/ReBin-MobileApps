package com.bangkit.rebinmobileapps.data.model

data class UserModel(
    val token: String,
    val email: String,
    val isLogin: Boolean = false
)
