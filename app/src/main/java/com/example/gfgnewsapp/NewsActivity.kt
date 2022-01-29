package com.example.gfgnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gfgnewsapp.adapters.NewsAdapter
import com.example.gfgnewsapp.repository.NewsRepository
import com.example.gfgnewsapp.ui.NewsViewModel
import com.example.gfgnewsapp.ui.NewsViewModelProvider
import com.example.gfgnewsapp.util.Resource

class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var rvNews: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        rvNews = findViewById(R.id.rv_news)
        refreshLayout = findViewById(R.id.refresh_layout)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProvider(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        setupRecyclerView()

        viewModel.news.observe(this, { newsResponse->
            when(newsResponse) {
                is Resource.Success -> {
                    newsResponse.data.let {
                        newsAdapter.differ.submitList(newsResponse.data?.items)
                    }
                }
            }
        })

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            viewModel.news.observe(this, { newsResponse->
                when(newsResponse) {
                    is Resource.Success -> {
                        newsResponse.data.let {
                            newsAdapter.differ.submitList(newsResponse.data?.items)
                        }
                    }
                }
            })
            refreshLayout.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(viewModel)
        rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(applicationContext)
//            addOnScrollListener(this@NewsActivity.scrollListener)
        }
    }
}