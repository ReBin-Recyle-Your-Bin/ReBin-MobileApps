package com.bangkit.rebinmobileapps.di

import android.content.Context
import com.bangkit.rebinmobileapps.data.UserPreferences
import com.bangkit.rebinmobileapps.data.api.ApiConfig
import com.bangkit.rebinmobileapps.data.dataStore
import com.bangkit.rebinmobileapps.data.UserRepository
import com.bangkit.rebinmobileapps.data.local.room.TrashDetectionDatabase
import com.bangkit.rebinmobileapps.data.repository.HistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking {
            pref.getSession().first()
        }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService, pref)
    }

    fun provideHistoryRepository(context: Context): HistoryRepository {
        val database = TrashDetectionDatabase.getDatabase(context)
        val dao = database.historyDao()
        val appExecutors = Executors.newSingleThreadExecutor()
        return HistoryRepository.getInstance(dao, appExecutors)
    }
}











