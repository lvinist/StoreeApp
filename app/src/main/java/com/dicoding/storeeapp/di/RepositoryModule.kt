package com.dicoding.storeeapp.di

import com.dicoding.storeeapp.di.repository.story.StoryRepository
import com.dicoding.storeeapp.di.repository.story.StoryRepositoryImpl
import com.dicoding.storeeapp.di.repository.user.UserRepository
import com.dicoding.storeeapp.di.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindStoryRepository(storyRepositoryImpl: StoryRepositoryImpl): StoryRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}