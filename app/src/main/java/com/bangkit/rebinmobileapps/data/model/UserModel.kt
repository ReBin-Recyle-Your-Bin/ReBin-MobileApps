package com.bangkit.rebinmobileapps.data.model

data class UserModel(
    val userId: String,
    val token: String,
    val name: String,
    val isLogin: Boolean = false
)
