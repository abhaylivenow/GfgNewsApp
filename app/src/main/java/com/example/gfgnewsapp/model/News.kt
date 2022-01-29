package com.example.gfgnewsapp.model


import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("feed")
    val feed: Feed,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("status")
    val status: String
)