package com.bangkit.rebinmobileapps.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CraftCategory(val imageResource: Int, val title: String)

data class StoryInpiration(
    val imageResource: Int,
    val title: String,
    val description: String
)

@Parcelize
data class Craft(
    val imageResource: Int,
    val title: String,
    val writer: String,
    val description: String
) : Parcelable