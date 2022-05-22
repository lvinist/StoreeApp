package com.dicoding.storeeapp.di.repository.story

import androidx.paging.PagingData
import com.dicoding.storeeapp.data.DefaultResponse
import com.dicoding.storeeapp.data.story.Story
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface StoryRepository {

    suspend fun addNewStoryWithToken(
        description: MultipartBody.Part,
        image: MultipartBody.Part,
        token: String
    ): DefaultResponse

    fun getStoriesWithToken(token: String): Flow<PagingData<Story>>
}