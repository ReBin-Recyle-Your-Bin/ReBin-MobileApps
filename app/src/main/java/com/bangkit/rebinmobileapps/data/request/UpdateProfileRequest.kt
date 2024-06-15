package com.bangkit.rebinmobileapps.data.request

data class UpdateProfileRequest(
    val newName: String,
    val email: String,
    val newPassword: String
)
