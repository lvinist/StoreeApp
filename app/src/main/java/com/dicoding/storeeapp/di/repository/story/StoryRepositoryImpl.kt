package com.dicoding.storeeapp.di.repository.story

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.storeeapp.data.DefaultResponse
import com.dicoding.storeeapp.data.paging.StoryPagingSource
import com.dicoding.storeeapp.data.story.Story
import com.dicoding.storeeapp.di.api.ApiService
import com.dicoding.storeeapp.utils.Constants.NETWORK_LOAD_SIZE
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(private val apiService: ApiService): StoryRepository {

    override suspend fun addNewStoryWithToken(
        description: MultipartBody.Part,
        image: MultipartBody.Part,
        token: String
    ): DefaultResponse =
        try {
            val response = apiService.uploadImage(image, description, token)
            response
        } catch (e: Exception) {
            DefaultResponse(error = true, e.localizedMessage!!.toString())
        }

    override fun getStoriesWithToken(token: String): Flow<PagingData<Story>> = Pager(
        config = PagingConfig(pageSize = NETWORK_LOAD_SIZE, enablePlaceholders = false),
        pagingSourceFactory = {
            StoryPagingSource(
                apiService = apiService,
                token = "Bearer $token"
            )
        }
    ).flow
}