package com.bangkit.rebinmobileapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("profile")
    val profileList: ProfileItem
):Parcelable

@Parcelize
data class ProfileItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

//    @SerializedName("phone")
//    val phone: String,
//
//    @SerializedName("address")
//    val address: String,
//
//    @SerializedName("photo")
//    val photo: String
):Parcelable