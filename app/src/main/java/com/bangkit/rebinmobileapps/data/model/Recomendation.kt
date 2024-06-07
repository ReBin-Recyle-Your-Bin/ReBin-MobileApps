package com.bangkit.rebinmobileapps.data.model

data class Recommendation(
    val Class: String,
    val cosine_similarity: Double,
    val id: String,
    val ingredients: String,
    val name: String,
    val pics_url: String,
    val steps: String
)

data class DetectionResult(
    val error: Boolean,
    val message: String,
    val accuracy: String,
    val label: String,
    val recommendation: List<Recommendation>
)
