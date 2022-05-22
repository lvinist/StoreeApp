package com.dicoding.storeeapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.storeeapp.data.story.Story
import com.dicoding.storeeapp.di.repository.story.StoryRepository
import com.dicoding.storeeapp.di.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository
    ): ViewModel() {

    val loginToken = userRepository.userToken

    private val _stories = MutableStateFlow<PagingData<Story>>(PagingData.empty())
    val stories = _stories.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            userRepository.userToken.collect { token ->
                storyRepository.getStoriesWithToken(token).collect {
                    _stories.value = it
                }
            }
        }
    }

    fun getLatestStories() {
        viewModelScope.launch {
            userRepository.userToken.collect { token ->
                storyRepository.getStoriesWithToken(token).collect {
                    _stories.value = it
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logoutUser()
        }
    }

}