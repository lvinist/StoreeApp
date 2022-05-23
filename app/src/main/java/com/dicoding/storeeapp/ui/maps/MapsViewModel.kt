package com.dicoding.storeeapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storeeapp.data.story.Story
import com.dicoding.storeeapp.di.repository.story.StoryRepository
import com.dicoding.storeeapp.di.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private var userRepository: UserRepository,
    private var storyRepository: StoryRepository
) : ViewModel() {

    private val _stories = MutableLiveData<List<Story>>()
    val stories: LiveData<List<Story>> get() = _stories

    init {
        viewModelScope.launch {
            val token = userRepository.getUserToken()
            val response = storyRepository.getStoriesWithTokenAndLocation(token)
            _stories.value = response.listStory.orEmpty()
        }
    }

}