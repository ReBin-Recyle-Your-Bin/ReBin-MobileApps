package com.bangkit.rebinmobileapps.view.detection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.rebinmobileapps.data.repository.DetectionResultRepository

class DetectionResultViewModelFactory(
    private val repository: DetectionResultRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetectionResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetectionResultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}