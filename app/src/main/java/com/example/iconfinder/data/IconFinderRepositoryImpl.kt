package com.example.iconfinder.data

import com.example.iconfinder.data.api.IconFinderApi
import com.example.iconfinder.models.CategorySetResponse
import com.example.iconfinder.models.IconSetResponse
import com.example.iconfinder.models.IconsResponse
import retrofit2.Response
import javax.inject.Inject

class IconFinderRepositoryImpl @Inject constructor(private val api: IconFinderApi) : IconFinderRepository {
    override suspend fun getIconCategorySets(): Response<CategorySetResponse> {
      return api.getIconSetsCategories()
    }

    override suspend fun searchIcons(query: String): Response<IconsResponse> {
        return api.searchPhotos(query)
    }

    override suspend fun getIconSets(identifier: String): Response<IconSetResponse> {
        return api.getIconSets(identifier)
    }

    override suspend fun getIconFromIconSets(id: Int): Response<IconsResponse> {
        return api.getIconFromIconSet(id)
    }
}