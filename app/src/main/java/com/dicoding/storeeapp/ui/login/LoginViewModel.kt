package com.dicoding.storeeapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storeeapp.data.user.Login
import com.dicoding.storeeapp.data.user.LoginResponse
import com.dicoding.storeeapp.di.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val loginResponse: LiveData<LoginResponse> get() = _loginResponse
    private val _loginResponse = MutableLiveData<LoginResponse>()

    fun login(loginBody: Login) {
        Log.d("LoginViewModel", loginBody.toString())
        viewModelScope.launch {
            _loginResponse.postValue(userRepository.loginUser(loginBody))
        }
    }

}