package com.bangkit.rebinmobileapps.data.api

import com.bangkit.rebinmobileapps.data.request.LoginRequest
import com.bangkit.rebinmobileapps.data.request.RegisterRequest
import com.bangkit.rebinmobileapps.data.request.UpdateProfileRequest
import com.bangkit.rebinmobileapps.data.response.DetectionResult
import com.bangkit.rebinmobileapps.data.response.HistoryDetectionResponse
import com.bangkit.rebinmobileapps.data.response.LoginResponse
import com.bangkit.rebinmobileapps.data.response.PointResponse
import com.bangkit.rebinmobileapps.data.response.ProfileResponse
import com.bangkit.rebinmobileapps.data.response.RegisterResponse
import com.bangkit.rebinmobileapps.data.response.SearchCraftResponse
import com.bangkit.rebinmobileapps.data.response.StoryResponse
import com.bangkit.rebinmobileapps.data.response.UpdateProfileResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @GET("craft/all")
    suspend fun getCraft(): SearchCraftResponse

    @GET("craft")
    suspend fun getCraftByCategory(
        @Query("className") category: String
    ): SearchCraftResponse

    @Multipart
    @POST("predict")
    fun uploadPhotoDetection(
        @Part photo: MultipartBody.Part,
//        @Header("Authorization") token: String
    ): Call<DetectionResult>

    @GET("story/all")
    suspend fun getStoryInspiration() : StoryResponse

    @GET("story/all")
    suspend fun getStory(): StoryResponse


    @GET("points")
    suspend fun getPoint(
        @Field("id") id: String,
    ) :PointResponse

    @GET("user/profile")
    suspend fun getProfile(
        @Query("_id") id: String,
    ) :ProfileResponse

    @PUT("user/profile")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ) : UpdateProfileResponse

    @GET("detect-waste/history")
    suspend fun getHistoryDetection(
        @Query("userId") id: String,
    ) :HistoryDetectionResponse


    @POST("detect-waste/history")
    fun sendDetectionResulToHistory(
        @Body requestBody: Map<String, String>
    ): Call<Void>
}