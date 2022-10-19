package com.example.iconfinder.models


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("license")
    val license: License,
    @SerializedName("price")
    val price: Double
)