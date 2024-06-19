package com.bangkit.rebinmobileapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoProfileResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val dataPhoto: List<PhotoProfileItem>
): Parcelable

@Parcelize
data class PhotoProfileItem(
    @SerializedName("public_id")
    val id: String,

    @SerializedName("display_name")
    val dispayName: String,

    @SerializedName("url")
    val photoUrl: String
): Parcelable
