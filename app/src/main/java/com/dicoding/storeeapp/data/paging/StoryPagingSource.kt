package com.dicoding.storeeapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.storeeapp.data.story.Story
import com.dicoding.storeeapp.di.api.ApiService
import com.dicoding.storeeapp.utils.Constants.NETWORK_LOAD_SIZE
import com.dicoding.storeeapp.utils.Constants.STORY_API_STARTING_INDEX
import retrofit2.HttpException
import java.io.IOException

class StoryPagingSource(private val apiService: ApiService, private val token: String) : PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val position = params.key ?: STORY_API_STARTING_INDEX
            val response = apiService.getStories(
                page = position,
                size = NETWORK_LOAD_SIZE,
                token = token)

            val stories = response.listStory
            val nextKey = if (stories.isNullOrEmpty()) null else position + 1
            LoadResult.Page(
                data = stories.orEmpty(),
                prevKey = if (position == STORY_API_STARTING_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}