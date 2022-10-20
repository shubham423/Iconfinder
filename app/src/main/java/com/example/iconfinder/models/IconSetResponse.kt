package com.example.iconfinder.models


import com.google.gson.annotations.SerializedName

data class IconSetResponse(
    @SerializedName("iconsets")
    val iconsets: List<Iconset>,
    @SerializedName("total_count")
    val totalCount: Int
)