package com.dicoding.storeeapp.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.storeeapp.data.DefaultResponse
import com.dicoding.storeeapp.data.user.Register
import com.dicoding.storeeapp.di.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    private val _response = MutableLiveData<DefaultResponse>()
    val response: LiveData<DefaultResponse> get() = _response

    suspend fun register(registerBody: Register){
        Log.d("login register", registerBody.toString())
        _response.value = userRepository.registerUser(registerBody)
    }
}