package com.example.iconfinder.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.iconfinder.BuildConfig
import com.example.iconfinder.data.api.BranchApi
import com.example.iconfinder.data.api.IconFinderApi
import com.example.iconfinder.utils.Constants.BRANCH_BASE_URL
import com.example.iconfinder.utils.Constants.ICON_FINDER_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun authInterceptor(): Interceptor {
        return Interceptor { chain ->
            var req = chain.request()
                req = req.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                    .build()
            chain.proceed(req)
        }

    }

    @Singleton
    @Provides
    fun provideHttpClient(@ApplicationContext applicationContext: Context) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(applicationContext))
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Singleton
    @Provides
    @Named("iconfinder")
    fun provideRetrofitInstanceForIconFinder(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        authInterceptor: Interceptor
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ICON_FINDER_BASE_URL)
            .client(okHttpClient.newBuilder().addInterceptor(authInterceptor).build())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
    @Singleton
    @Provides
    @Named("branch")
    fun provideRetrofitInstanceForBranch(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BRANCH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
    @Singleton
    @Provides
    fun provideIconFinderApiService(@Named("iconfinder") retrofit: Retrofit): IconFinderApi {
        return retrofit.create(IconFinderApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBranchApiService(@Named("branch") retrofit: Retrofit): BranchApi {
        return retrofit.create(BranchApi::class.java)
    }
}