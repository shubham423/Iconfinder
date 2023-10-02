package com.example.iconfinder.data.api

import com.example.iconfinder.models.BranchEventRequest
import com.example.iconfinder.models.CategorySetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BranchApi {
    @POST("event/standard")
    suspend fun logStandardEvent(
        @Body() branchEventRequest: BranchEventRequest
    ): Response<Any>

    @POST("event/custom")
    suspend fun logCustomEvent(
        @Body() branchEventRequest: BranchEventRequest
    ): Response<Any>
}