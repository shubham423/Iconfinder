package com.example.iconfinder.di

import com.example.iconfinder.MyApplication
import com.example.iconfinder.data.BranchRepository
import com.example.iconfinder.data.BranchRepositoryImpl
import com.example.iconfinder.data.IconFinderRepository
import com.example.iconfinder.data.IconFinderRepositoryImpl
import com.example.iconfinder.data.api.BranchApi
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
    fun provideIconFinderRepository(api: IconFinderApi): IconFinderRepository {
        return IconFinderRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideBranchRepository(api: BranchApi): BranchRepository {
        return BranchRepositoryImpl(api)
    }
}