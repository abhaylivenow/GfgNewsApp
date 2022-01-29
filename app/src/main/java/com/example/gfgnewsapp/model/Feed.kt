package com.example.gfgnewsapp.model


import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("author")
    val author: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)