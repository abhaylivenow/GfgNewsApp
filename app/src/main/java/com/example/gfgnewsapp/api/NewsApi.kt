package com.example.gfgnewsapp.api

import com.example.gfgnewsapp.model.News
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {
    @GET("v1/api.json?rss_url=http://www.abc.net.au/news/feed/51120/rss.xml")
    suspend fun getNews() : Response<News>
}