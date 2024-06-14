package com.bangkit.rebinmobileapps.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Craft(
    val imageResource: Int,
    val title: String,
    val className: String,
    val description: String
) : Parcelable
