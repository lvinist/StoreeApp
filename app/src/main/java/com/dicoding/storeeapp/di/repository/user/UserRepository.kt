package com.dicoding.storeeapp.di.repository.user

import com.dicoding.storeeapp.data.DefaultResponse
import com.dicoding.storeeapp.data.user.Login
import com.dicoding.storeeapp.data.user.LoginResponse
import com.dicoding.storeeapp.data.user.Register
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(registerBody: Register): DefaultResponse

    suspend fun loginUser(loginBody: Login): LoginResponse

    suspend fun logoutUser()

    val userToken: Flow<String>

    suspend fun getUserToken(): String

    suspend fun setUserToken(token: String)
}