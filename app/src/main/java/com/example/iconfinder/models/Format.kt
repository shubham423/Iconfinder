package com.example.iconfinder.models


import com.google.gson.annotations.SerializedName

data class Format(
    @SerializedName("download_url")
    val downloadUrl: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("preview_url")
    val previewUrl: String
)