package com.bangkit.rebinmobileapps.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeatureHome(
    val imageResource: Int, val title: String
) : Parcelable
