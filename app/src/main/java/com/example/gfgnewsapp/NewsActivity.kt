package com.example.gfgnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.gfgnewsapp.repository.NewsRepository
import com.example.gfgnewsapp.ui.NewsViewModel
import com.example.gfgnewsapp.ui.NewsViewModelProvider
import com.example.gfgnewsapp.util.Resource

class NewsActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProvider(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        viewModel.news.observe(this, { newsResponse->
            when(newsResponse) {
                is Resource.Success -> {
                    newsResponse.data.let {
                    }
                }
            }
        })
    }
}