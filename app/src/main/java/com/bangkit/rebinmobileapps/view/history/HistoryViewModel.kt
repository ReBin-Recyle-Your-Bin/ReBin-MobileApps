package com.bangkit.rebinmobileapps.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.rebinmobileapps.data.local.entity.History
import com.bangkit.rebinmobileapps.data.repository.HistoryRepository

class HistoryViewModel(
    private val historyRepository: HistoryRepository
): ViewModel() {

    fun getAll(): LiveData<List<History>> {
        return historyRepository.getAll()
    }

    fun insert(history: History) {
        historyRepository.insert(history)
    }

    fun delete(history: History) {
        historyRepository.delete(history)
    }

    fun deleteAll() {
        historyRepository.deleteAll()
    }
}













