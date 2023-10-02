package com.example.iconfinder.models

import com.example.iconfinder.BuildConfig
import com.google.gson.annotations.SerializedName

data class BranchEventRequest(
    val name: String,

    @SerializedName("branch_key")
    val branchKey: String=BuildConfig.BRANCH_KEY,

    @SerializedName("user_data")
    val userData: UserData
) {
    data class UserData(
        val aaid: String=BuildConfig.AAID,
        val os:String="android"
    )
}
