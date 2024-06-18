package com.bangkit.rebinmobileapps.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormat {
    fun getRelativeTime(dateString: String): String? {
        return parseDate(dateString)
    }

    private fun parseDate(dateString: String): String? {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
            date?.let { outputFormat.format(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}