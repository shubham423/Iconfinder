package com.example.iconfinder.models


import com.google.gson.annotations.SerializedName

data class SearchIconResponse(
    @SerializedName("icons")
    val icons: List<Icon>,
    @SerializedName("total_count")
    val totalCount: Int
)