package com.example.gfgnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gfgnewsapp.R
import com.example.gfgnewsapp.adapters.NewsAdapter
import com.example.gfgnewsapp.repository.NewsRepository
import com.example.gfgnewsapp.util.Resource

class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var rvNews: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        rvNews = findViewById(R.id.rv_news)
        refreshLayout = findViewById(R.id.refresh_layout)
        progressBar = findViewById(R.id.progress)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProvider(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        setupRecyclerView()

        observeData()

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            observeData()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeData() {
        viewModel.news.observe(this, { newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    hideProgressBar()
                    newsResponse.data.let {
                        newsAdapter.differ.submitList(newsResponse.data?.items)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    newsResponse.message?.let { message ->
                        Log.i("Error", message)
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
}