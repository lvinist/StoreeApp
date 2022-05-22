package com.dicoding.storeeapp.di.repository.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.storeeapp.data.DefaultResponse
import com.dicoding.storeeapp.data.datastore.DatastoreManager
import com.dicoding.storeeapp.data.paging.StoryPagingSource
import com.dicoding.storeeapp.data.story.Story
import com.dicoding.storeeapp.data.user.Login
import com.dicoding.storeeapp.data.user.LoginResponse
import com.dicoding.storeeapp.data.user.Register
import com.dicoding.storeeapp.data.user.User
import com.dicoding.storeeapp.di.api.ApiService
import com.dicoding.storeeapp.utils.Constants.NETWORK_LOAD_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService, private val dataStoreManager: DatastoreManager): UserRepository {

    private suspend fun addTokenToPreferences(loginToken: String) {
        dataStoreManager.updateLoginToken(loginToken)
    }

    override suspend fun registerUser(registerBody: Register): DefaultResponse =
        try {
            val (name, email, password) = registerBody
            if (name!!.isEmpty() or email!!.isEmpty() or password!!.isEmpty())
                throw Exception()
            val response = apiService.registerUser(registerBody)
            response
        } catch (e: Exception) {
            DefaultResponse(error = true, e.message.toString())
        }

    override suspend fun logoutUser() {
        dataStoreManager.clearUserToken()
    }

    override suspend fun loginUser(loginBody: Login): LoginResponse = try {
        val (email, password) = loginBody
        if (email!!.isEmpty() or password!!.isEmpty())
            throw Exception()
        val response = apiService.loginUser(loginBody)
        response.loginResult!!.token.let { addTokenToPreferences(it.toString()) }
        response
    } catch (e: Exception) {
        LoginResponse(error = true, message = e.localizedMessage as String, loginResult = User("","",""))
    }

    override val userToken: Flow<String> get() = dataStoreManager.preferenceLoginToken

    override suspend fun getUserToken(): String {
        val flowOfToken = dataStoreManager.preferenceLoginToken
        return flowOfToken.first()
    }

    override suspend fun setUserToken(token: String) {
        dataStoreManager.updateLoginToken(token)
    }
}