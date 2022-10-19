package com.example.iconfinder.data.api

import com.example.iconfinder.models.CategorySetResponse
import retrofit2.Response
import retrofit2.http.GET

interface IconFinderApi {

    @GET("categories")
    suspend fun getIconSetsCategories() :Response<CategorySetResponse>
}