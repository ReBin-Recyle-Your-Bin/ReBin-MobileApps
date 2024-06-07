package com.bangkit.rebinmobileapps.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Craft(
    val imageResource: Int,
    val title: String,
    val writer: String,
    val description: String
) : Parcelable
