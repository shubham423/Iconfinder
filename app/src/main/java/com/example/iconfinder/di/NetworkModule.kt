package com.example.iconfinder.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.iconfinder.MyApplication
import com.example.iconfinder.data.api.IconFinderApi
import com.example.iconfinder.utils.Constants.API_KEY
import com.example.iconfinder.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
                    .header("Authorization", "Bearer $API_KEY")
                    .build()
            chain.proceed(req)
        }

    }

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(MyApplication.applicationContext()))
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
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        authInterceptor: Interceptor
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient.newBuilder().addInterceptor(authInterceptor).build())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): IconFinderApi {
        return retrofit.create(IconFinderApi::class.java)
    }
}