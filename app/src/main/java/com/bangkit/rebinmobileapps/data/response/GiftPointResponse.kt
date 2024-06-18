package com.bangkit.rebinmobileapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class GiftPointResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("tukarpoint")
    val giftPointList: List<GiftPointItem>
): Parcelable

@Parcelize
data class GiftPointItem(
    @SerializedName("_id")
    val id: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("point")
    val point: String,

    @SerializedName("decription")
    val description: String,

    @SerializedName("photoUrl")
    val photoUrl: String
): Parcelable