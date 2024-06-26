package com.bangkit.rebinmobileapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val listPoint: List<PointItem>
): Parcelable

@Parcelize
data class PointItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("point")
    val point: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("status")
    val status: String
): Parcelable
