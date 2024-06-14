package com.bangkit.rebinmobileapps.data.repository

import androidx.lifecycle.LiveData
import com.bangkit.rebinmobileapps.data.local.entity.History
import com.bangkit.rebinmobileapps.data.local.room.HistoryDao
import java.util.concurrent.ExecutorService

class HistoryRepository(
    private val historyDao: HistoryDao,
    private val executorService: ExecutorService
) {
    fun insert(history: History) {
        executorService.execute { historyDao.insert(history) }
    }

    fun delete(history: History) {
        executorService.execute { historyDao.delete(history) }
    }

    fun deleteAll() {
        executorService.execute { historyDao.deleteAll() }
    }

    fun getAll(): LiveData<List<History>> = historyDao.getAll()

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null

        fun clearInstance() {
            instance = null
        }

        fun getInstance(
            historyDao: HistoryDao,
            executorService: ExecutorService
        ): HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(historyDao, executorService)
            }.also { instance = it }
    }
}












