package com.bangkit.rebinmobileapps.data.response

import com.google.gson.annotations.SerializedName

data class Recommendation(
    @SerializedName("Class")
    val Class: String,

    @SerializedName("cosine_similarity")
    val cosine_similarity: Double,

    @SerializedName("id")
    val id: String,

    @SerializedName("ingredients")
    val ingredients: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("pics_url")
    val pics_url: String,

    @SerializedName("steps")
    val steps: String
)

data class DetectionResult(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("accuracy")
    val accuracy: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("recommendation")
    val recommendation: List<Recommendation>
)
