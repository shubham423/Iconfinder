package com.example.iconfinder.data

import com.example.iconfinder.models.BranchEventRequest
import retrofit2.Response

interface BranchRepository {

    suspend fun logStandardEvent(branchEventRequest: BranchEventRequest):Response<Any>
    suspend fun logCustomEvent(branchEventRequest: BranchEventRequest):Response<Any>
}