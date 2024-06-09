package com.bangkit.rebinmobileapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CraftsResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("listItems")
    val listItems: List<CraftItem>
) : Parcelable

@Parcelize
data class CraftItem(
    @SerializedName("_id")
    val id: String,

    @SerializedName("id")
    val itemId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("class")
    val itemClass: String,

    @SerializedName("description")
    val decripstion: String,

    @SerializedName("ingeredients")
    val ingredients: String,

    @SerializedName("steps")
    val steps: String,

    @SerializedName("photoUrl")
    val photoUrl: String
) : Parcelable