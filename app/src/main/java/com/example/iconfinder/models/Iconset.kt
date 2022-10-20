package com.example.iconfinder.models


import com.google.gson.annotations.SerializedName

data class Iconset(
    @SerializedName("are_all_icons_glyph")
    val areAllIconsGlyph: Boolean,
    @SerializedName("author")
    val author: Author,
    @SerializedName("categories")
    val categories: List<CategoryX>,
    @SerializedName("icons_count")
    val iconsCount: Int,
    @SerializedName("iconset_id")
    val iconsetId: Int,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("is_premium")
    val isPremium: Boolean,
    @SerializedName("is_purchased")
    val isPurchased: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("prices")
    val prices: List<PriceX>,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("styles")
    val styles: List<StyleX>,
    @SerializedName("type")
    val type: String
)