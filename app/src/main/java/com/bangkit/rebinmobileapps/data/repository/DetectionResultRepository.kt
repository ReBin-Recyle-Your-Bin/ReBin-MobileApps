package com.bangkit.rebinmobileapps.data.repository

import com.bangkit.rebinmobileapps.data.local.entity.DetectionResultEntity
import com.bangkit.rebinmobileapps.data.local.room.DetectionResultDao
import java.util.concurrent.ExecutorService

class DetectionResultRepository(
    private val detectionResultDao: DetectionResultDao,
    private val executorService: ExecutorService
) {

    fun insertDetectionResult(detectionResultEntity: DetectionResultEntity) {
        executorService.execute { detectionResultDao.insertDetectionResult(detectionResultEntity) }
    }

    fun deleteDetectionResult(detectionResultEntity: DetectionResultEntity) {
        executorService.execute { detectionResultDao.deleteDetectionResults(detectionResultEntity) }
    }

    fun deleteAllDetectionResults() {
        executorService.execute { detectionResultDao.deleteAllDetectionResults() }
    }

    fun getAllDetectionResults() = detectionResultDao.getAllDetectionResults()
    companion object {
        @Volatile
        private var instance: DetectionResultRepository? = null
        fun getInstance(
            detectionResultDao: DetectionResultDao,
            executorService: ExecutorService
        ): DetectionResultRepository =
            instance ?: synchronized(this) {
                instance ?: DetectionResultRepository(detectionResultDao, executorService)
            }.also { instance = it }
    }
}