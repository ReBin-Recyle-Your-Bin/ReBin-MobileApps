package com.bangkit.rebinmobileapps.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recommendation(
    @SerializedName("Description")
    val description: String,

    @SerializedName("class")
    val classItem: String,

    @SerializedName("combined_text")
    val combined_text: String,

    @SerializedName("cosine_similarity")
    val cosine_similarity: Double,

    @SerializedName("id")
    val id: String,

    @SerializedName("ingredients")
    val ingredients: String,

    @SerializedName("languange")
    val languange: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("pics_url")
    val pics_url: String,

    @SerializedName("steps")
    val steps: String
) : Parcelable

@Parcelize
data class DetectionResult(
//    @SerializedName("error")
//    val error: Boolean,
//
//    @SerializedName("message")
//    val message: String,

    @SerializedName("accuracy")
    val accuracy: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("recommendation")
    val recommendation: List<Recommendation>
) : Parcelable
