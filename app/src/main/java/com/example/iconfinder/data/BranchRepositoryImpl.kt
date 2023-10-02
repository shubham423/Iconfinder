package com.example.iconfinder.data

import com.example.iconfinder.data.api.BranchApi
import com.example.iconfinder.models.BranchEventRequest
import retrofit2.Response
import javax.inject.Inject

class BranchRepositoryImpl @Inject constructor(private val branchApi: BranchApi) : BranchRepository {
    override suspend fun logStandardEvent(branchEventRequest: BranchEventRequest): Response<Any> {
        return branchApi.logStandardEvent(branchEventRequest)
    }

    override suspend fun logCustomEvent(branchEventRequest: BranchEventRequest): Response<Any> {
        return branchApi.logCustomEvent(branchEventRequest)
    }
}