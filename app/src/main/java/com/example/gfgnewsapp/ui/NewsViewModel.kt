package com.example.gfgnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gfgnewsapp.model.News
import com.example.gfgnewsapp.repository.NewsRepository
import com.example.gfgnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val news: MutableLiveData<Resource<News>> = MutableLiveData()
    var newsResponse: News? = null

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        news.postValue(Resource.Loading())
        val response = newsRepository.getNews()
        news.postValue(handleNewsResponse(response))
    }

    private fun handleNewsResponse(
        response: Response<News>
    ): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                newsResponse = resultResponse
                return Resource.Success(newsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}