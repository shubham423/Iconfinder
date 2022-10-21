package com.example.iconfinder.models


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("price")
    val price: Double
)