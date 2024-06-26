package com.bangkit.rebinmobileapps.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CraftCategory(
    val imageResource: Int, val title: String
) : Parcelable

