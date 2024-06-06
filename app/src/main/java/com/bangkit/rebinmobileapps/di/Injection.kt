package com.bangkit.rebinmobileapps.di

import android.content.Context
import com.bangkit.rebinmobileapps.data.UserPrefences
import com.bangkit.rebinmobileapps.data.api.ApiConfig
import com.bangkit.rebinmobileapps.data.dataStore
import com.bangkit.rebinmobileapps.data.repository.AuthRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPrefences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService, pref)
    }
}