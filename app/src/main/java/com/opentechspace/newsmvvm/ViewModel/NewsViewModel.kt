package com.opentechspace.newsmvvm.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.opentechspace.newsmvvm.Model.Article
import com.opentechspace.newsmvvm.Model.NewsResponse
import com.opentechspace.newsmvvm.Repository.NewsRepository
import com.opentechspace.newsmvvm.Resource.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val mutableBreakingNews = MutableLiveData<Resource<NewsResponse>>()
    val breakingNews : LiveData<Resource<NewsResponse>> = mutableBreakingNews
    var breakingNewsPage = 1

    private val mutableSearchNews = MutableLiveData<Resource<NewsResponse>>()
    val searchNewsResponse : LiveData<Resource<NewsResponse>> = mutableSearchNews
    var searchNewsPage = 1


    init {
        getBreakingNews("in")
    }


    fun getBreakingNews(countryCode : String) = viewModelScope.launch {
       mutableBreakingNews.postValue(Resource.Loading())
        val response = repository.getBreakingNews(countryCode,breakingNewsPage)
        mutableBreakingNews.postValue(handleBreakingNewsResponse(response))
        Log.e("page", breakingNewsPage.toString())
    }
    fun getSearchNews(searchQuery : String) = viewModelScope.launch {
        mutableSearchNews.postValue(Resource.Loading())
        val response = repository.searchNews(searchQuery,searchNewsPage)
        mutableSearchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful)
        {
            response.body()?.let {newsResponse ->
                return Resource.Success(newsResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful)
        {
            response.body()?.let {newsResponse ->
                return Resource.Success(newsResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun insertDb(article: Article): Job {
        return viewModelScope.launch {
            repository.insertDB(article)
        }
    }
    fun getAllSavedDBNews() = repository.getAllDBNews()

    fun deleteDb(article: Article) = viewModelScope.launch {
        repository.deleteDB(article)
    }

}