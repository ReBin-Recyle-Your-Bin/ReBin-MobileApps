package com.bangkit.rebinmobileapps.data.api

import com.bangkit.rebinmobileapps.data.request.LoginRequest
import com.bangkit.rebinmobileapps.data.request.RegisterRequest
import com.bangkit.rebinmobileapps.data.response.CraftsResponse
import com.bangkit.rebinmobileapps.data.response.DetectionResult
import com.bangkit.rebinmobileapps.data.response.HistoryDetectionResponse
import com.bangkit.rebinmobileapps.data.response.LoginResponse
import com.bangkit.rebinmobileapps.data.response.PointResponse
import com.bangkit.rebinmobileapps.data.response.ProfileResponse
import com.bangkit.rebinmobileapps.data.response.RegisterResponse
import com.bangkit.rebinmobileapps.data.response.SearchCraftResponse
import com.bangkit.rebinmobileapps.data.response.StoryResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
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
    suspend fun getCraft(): CraftsResponse

    @GET("craft")
    suspend fun getCraftByCategory(
        @Query("className") category: String
    ): CraftsResponse

    @Multipart
    @POST("predict")
    fun uploadPhotoDetection(
        @Part photo: MultipartBody.Part,
//        @Header("Authorization") token: String
    ): Call<DetectionResult>


    @GET("stories")
    suspend fun getStoryInspiration() : StoryResponse

    @GET("points")
    suspend fun getPoint(
        @Field("id") id: String,
    ) :PointResponse

    @GET("user/profile")
    suspend fun getProfile(
        @Query("_id") id: String,
    ) :ProfileResponse

    @GET("detect-waste/history")
    suspend fun getHistoryDetection(
        @Query("userId") id: String,
    ) :HistoryDetectionResponse


    @POST("detect-waste/history")
    fun sendDetectionResulToHistory(
        @Body requestBody: Map<String, String>
    ): Call<Void>
}