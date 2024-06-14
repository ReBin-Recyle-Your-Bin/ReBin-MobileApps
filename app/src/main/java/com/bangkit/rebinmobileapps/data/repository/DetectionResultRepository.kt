package com.bangkit.rebinmobileapps.data.repository

import com.bangkit.rebinmobileapps.data.api.ApiService
import com.bangkit.rebinmobileapps.data.local.entity.DetectionResultEntity
import com.bangkit.rebinmobileapps.data.local.room.DetectionResultDao
import java.util.concurrent.ExecutorService

class DetectionResultRepository private constructor(
    private val apiService: ApiService
) {

    fun getAllHistoryUserId() {

    }

    companion object {
        @Volatile
        private var instance: DetectionResultRepository? = null

        fun clearInstance() {
            instance = null
        }

        fun getInstance(
            apiService: ApiService
        ): DetectionResultRepository =
            instance ?: synchronized(this) {
                instance ?: DetectionResultRepository(apiService)
            }.also { instance = it }
    }
}