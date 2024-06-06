package com.bangkit.rebinmobileapps.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.data.UserPrefences
import com.bangkit.rebinmobileapps.data.api.ApiService
import com.bangkit.rebinmobileapps.data.model.UserModel
import com.bangkit.rebinmobileapps.data.response.LoginResponse
import com.bangkit.rebinmobileapps.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPrefences
){
    fun register(
        name: String,
        email:String,
        password: String
    ): LiveData<ResultState<RegisterResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun login(
        email: String,
        password: String
    ): LiveData<ResultState<LoginResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.login(email, password)
            userPreferences.saveSession(UserModel(email, response.loginResult.token, true))
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    suspend fun logout() {
        userPreferences.logout()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPrefences
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, userPreferences)
            }.also { instance = it }
    }
}















