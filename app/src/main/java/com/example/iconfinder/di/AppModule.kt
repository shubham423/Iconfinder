package com.example.iconfinder.di

import com.example.iconfinder.MyApplication
import com.example.iconfinder.data.IconFinderRepository
import com.example.iconfinder.data.IconFinderRepositoryImpl
import com.example.iconfinder.data.api.IconFinderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext() = MyApplication()

    @Singleton
    @Provides
    fun provideRepository(api: IconFinderApi): IconFinderRepository {
        return IconFinderRepositoryImpl(api)
    }
}