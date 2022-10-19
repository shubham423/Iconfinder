package com.example.iconfinder.models


import com.google.gson.annotations.SerializedName

data class CategorySetResponse(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("total_count")
    val totalCount: Int
)