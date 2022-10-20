package com.example.iconfinder.data

import com.example.iconfinder.models.CategorySetResponse
import com.example.iconfinder.models.IconsResponse
import retrofit2.Response

interface IconFinderRepository  {
    suspend fun getIconCategorySets():Response<CategorySetResponse>
    suspend fun searchIcons(query: String):Response<IconsResponse>
}