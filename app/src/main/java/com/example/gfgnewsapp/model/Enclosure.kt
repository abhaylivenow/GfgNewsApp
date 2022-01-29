package com.example.gfgnewsapp.model


import com.google.gson.annotations.SerializedName

data class Enclosure(
    @SerializedName("link")
    val link: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("type")
    val type: String
)