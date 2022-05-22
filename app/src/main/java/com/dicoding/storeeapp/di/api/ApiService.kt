package com.dicoding.storeeapp.di.api

import com.dicoding.storeeapp.data.DefaultResponse
import com.dicoding.storeeapp.data.story.Story
import com.dicoding.storeeapp.data.story.StoryResponse
import com.dicoding.storeeapp.data.user.Login
import com.dicoding.storeeapp.data.user.LoginResponse
import com.dicoding.storeeapp.data.user.Register
import com.dicoding.storeeapp.utils.Constants.NETWORK_LOAD_SIZE
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @POST("login")
    suspend fun loginUser(@Body loginBody: Login): LoginResponse

    @POST("register")
    suspend fun registerUser(@Body registerBody: Register): DefaultResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = NETWORK_LOAD_SIZE,
        @Header("Authorization") token: String
    ): StoryResponse<Story>

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part description: MultipartBody.Part,
        @Header("Authorization") token: String
    ): DefaultResponse

}