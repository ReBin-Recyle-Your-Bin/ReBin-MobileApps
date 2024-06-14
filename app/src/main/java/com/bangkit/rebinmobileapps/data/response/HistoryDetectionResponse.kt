package com.bangkit.rebinmobileapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryDetectionResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val listHistoryDetection: List<HistoryDetectionItem>
):Parcelable

@Parcelize
data class HistoryDetectionItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("accuracy")
    val accuracy: String,

    @SerializedName("date")
    val date: String,
):Parcelable
