package com.example.gfgnewsapp.repository

import com.example.gfgnewsapp.api.RetrofitBuilder

class NewsRepository {
    suspend fun getNews() = RetrofitBuilder.api.getNews()
}