package com.example.iconfinder.data.api

import com.example.iconfinder.models.CategorySetResponse
import com.example.iconfinder.models.IconSetResponse
import com.example.iconfinder.models.IconsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IconFinderApi {

    @GET("categories")
    suspend fun getIconSetsCategories(
        @Query("count") count: Int=25,
    ) :Response<CategorySetResponse>

    @GET("icons/search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("count") count: Int=15,
    ): Response<IconsResponse>

    @GET("categories/{category_identifier}/iconsets")
    suspend fun getIconSets(
        @Path("category_identifier") identifier: String,
        @Query("count") count: Int=25,
    ): Response<IconSetResponse>

    @GET("iconsets/{iconset_id}/icons")
    suspend fun getIconFromIconSet(
        @Path("iconset_id") id: Int,
        @Query("count") count: Int=15,
    ): Response<IconsResponse>
}