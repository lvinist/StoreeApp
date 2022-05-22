package com.dicoding.storeeapp.di

import android.content.Context
import com.dicoding.storeeapp.data.datastore.DatastoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DatastoreManager =
        DatastoreManager(context)

}