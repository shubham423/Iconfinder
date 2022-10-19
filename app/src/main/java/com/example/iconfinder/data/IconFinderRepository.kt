package com.example.iconfinder.data

import com.example.iconfinder.models.CategorySetResponse
import retrofit2.Response

interface IconFinderRepository  {
    suspend fun getIconCategorySets():Response<CategorySetResponse>
}