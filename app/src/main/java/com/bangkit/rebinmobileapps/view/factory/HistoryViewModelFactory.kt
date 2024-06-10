package com.bangkit.rebinmobileapps.view.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.rebinmobileapps.data.repository.HistoryRepository
import com.bangkit.rebinmobileapps.di.Injection
import com.bangkit.rebinmobileapps.view.history.HistoryViewModel

class HistoryViewModelFactory private constructor(
    private val historyRepository: HistoryRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(historyRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: HistoryViewModelFactory? = null

        fun getInstance(context: Context): HistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryViewModelFactory(Injection.provideHistoryRepository(context))
            }.also { instance = it }
    }
}