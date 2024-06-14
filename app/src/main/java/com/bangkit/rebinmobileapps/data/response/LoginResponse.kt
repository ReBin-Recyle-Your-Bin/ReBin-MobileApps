package com.bangkit.rebinmobileapps.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class Data(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val userId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("token")
    val token: String
)