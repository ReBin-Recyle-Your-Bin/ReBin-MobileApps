package com.bangkit.rebinmobileapps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.bangkit.rebinmobileapps.data.api.ApiService
import com.bangkit.rebinmobileapps.data.model.UserModel
import com.bangkit.rebinmobileapps.data.request.LoginRequest
import com.bangkit.rebinmobileapps.data.request.RegisterRequest
import com.bangkit.rebinmobileapps.data.request.UpdateProfileRequest
import com.bangkit.rebinmobileapps.data.response.ChallengeItem
import com.bangkit.rebinmobileapps.data.response.ErrorResponse
import com.bangkit.rebinmobileapps.data.response.HistoryDetectionItem
import com.bangkit.rebinmobileapps.data.response.LoginResponse
import com.bangkit.rebinmobileapps.data.response.PointItem
import com.bangkit.rebinmobileapps.data.response.ProfileItem
import com.bangkit.rebinmobileapps.data.response.RegisterResponse
import com.bangkit.rebinmobileapps.data.response.SearchCraftItems
import com.bangkit.rebinmobileapps.data.response.StoryItem
import com.bangkit.rebinmobileapps.data.response.UpdateProfileResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
){
    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<ResultState<RegisterResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val request = RegisterRequest(name, email, password)
            val response = apiService.register(request)
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }

    fun login(
        email: String,
        password: String
    ): LiveData<ResultState<LoginResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            //simpan sesi user setelah login
            saveSession(UserModel(response.data.userId,response.data.token,response.data.name,true))
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }
    fun getStoryInspiration(): LiveData<ResultState<List<StoryItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getStoryInspiration()
            emit(ResultState.Success(response.listStoryInpiration))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }
    fun getHistoryDetection(): LiveData<ResultState<List<HistoryDetectionItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val userId = userPreferences.getSession().first().userId
            val response = apiService.getHistoryDetection(userId)
            emit(ResultState.Success(response.listHistoryDetection))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }

    fun getCraftByCategory(category: String): LiveData<ResultState<List<SearchCraftItems>>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getCraftByCategory(category)
            emit(ResultState.Success(response.listItemCraft))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }

    fun getPointHistory(): LiveData<ResultState<List<PointItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val userId = userPreferences.getSession().first().userId
            val response = apiService.getPoint(userId)
            emit(ResultState.Success(response.listPoint))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }

    fun getCraft(): LiveData<ResultState<List<SearchCraftItems>>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getCraft()
            emit(ResultState.Success(response.listItemCraftPaging))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }

    fun getCrafties() : LiveData<PagingData<SearchCraftItems>>{
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                CraftPagingSource(apiService)
            }
        ).liveData
    }

    fun getProfile(): LiveData<ResultState<ProfileItem>> = liveData {
        emit(ResultState.Loading)
        try {
            val userId = userPreferences.getSession().first().userId
            val response = apiService.getProfile(userId)
            emit(ResultState.Success(response.profileList.first()))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }

    fun getChallenge(): LiveData<ResultState<List<ChallengeItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getChallenge()
            emit(ResultState.Success(response.challengeList))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }

    fun updateProfile(
        newName: String,
        email: String,
        newPassword: String
    ): LiveData<ResultState<UpdateProfileResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val request = UpdateProfileRequest(newName, email, newPassword)
            val response = apiService.updateProfile(request)
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occured"))
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreferences.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    suspend fun logout() {
        userPreferences.logout()
    }

    companion object {

        private var INSTANCE: UserRepository? = null

        fun clearInstance() {
            INSTANCE = null
        }
        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences
        ): UserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService, userPreferences)
            }.also { INSTANCE = it }
    }
}















