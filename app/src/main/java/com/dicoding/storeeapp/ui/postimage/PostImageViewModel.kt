package com.dicoding.storeeapp.ui.postimage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storeeapp.data.DefaultResponse
import com.dicoding.storeeapp.di.repository.story.StoryRepository
import com.dicoding.storeeapp.di.repository.user.UserRepository
import com.dicoding.storeeapp.utils.Utils.toMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostImageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {
    private val _image = MutableLiveData<File>()
    val image: LiveData<File> get() = _image

    private val _response = MutableLiveData<DefaultResponse>()
    val addStoryResponse: LiveData<DefaultResponse> get() = _response

    fun saveImage(image: File) {
        viewModelScope.launch {
            _image.postValue(image)
        }
    }

    fun addNewStoryWithToken(description: String, image: File) {
        viewModelScope.launch {
            val imageMultiPart = image.toMultipart()
            val descriptionMultipart = description.toMultipart()
            val token = userRepository.getUserToken()
            _response.value = storyRepository.addNewStoryWithToken(
                descriptionMultipart,
                imageMultiPart,
                "Bearer $token"
            )
            Log.d("post", _response.value.toString())
        }

    }
}