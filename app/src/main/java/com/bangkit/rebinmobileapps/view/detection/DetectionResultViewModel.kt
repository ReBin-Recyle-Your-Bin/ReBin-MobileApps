package com.bangkit.rebinmobileapps.view.detection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.rebinmobileapps.data.local.entity.DetectionResultEntity
import com.bangkit.rebinmobileapps.data.repository.DetectionResultRepository

class DetectionResultViewModel(
    private val detectionHistoryRepository: DetectionResultRepository

): ViewModel() {
    fun getAll(): LiveData<List<DetectionResultEntity>> {
        return detectionHistoryRepository.getAllDetectionResults()
    }

    fun insert(detectionResult: DetectionResultEntity) {
        detectionHistoryRepository.insertDetectionResult(detectionResult)
    }

    fun delete(detectionResult: DetectionResultEntity) {
        detectionHistoryRepository.deleteDetectionResult(detectionResult)
    }

    fun deleteAll() {
        detectionHistoryRepository.deleteAllDetectionResults()
    }

}