package com.example.iconfinder.data

import com.example.iconfinder.data.api.IconFinderApi
import com.example.iconfinder.models.CategorySetResponse
import retrofit2.Response
import javax.inject.Inject

class IconFinderRepositoryImpl @Inject constructor(private val api: IconFinderApi) : IconFinderRepository {
    override suspend fun getIconCategorySets(): Response<CategorySetResponse> {
      return api.getIconSetsCategories()
    }
}