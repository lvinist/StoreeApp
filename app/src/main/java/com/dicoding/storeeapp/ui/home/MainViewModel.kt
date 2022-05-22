package com.dicoding.storeeapp.ui.home

import androidx.lifecycle.ViewModel
import com.dicoding.storeeapp.di.repository.story.StoryRepository
import com.dicoding.storeeapp.di.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val storyRepository: StoryRepository, private val userRepository: UserRepository): ViewModel() {

    val loginToken = userRepository.userToken

}