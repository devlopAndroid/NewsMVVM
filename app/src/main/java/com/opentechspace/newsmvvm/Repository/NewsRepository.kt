package com.opentechspace.newsmvvm.Repository

import com.opentechspace.newsmvvm.Api.RetrofitInstance
import com.opentechspace.newsmvvm.Model.Article
import com.opentechspace.newsmvvm.Model.NewsResponse
import com.opentechspace.newsmvvm.db.LocalDatabase
import com.opentechspace.newsmvvm.utils.Constants
import retrofit2.Response

class NewsRepository(val db : LocalDatabase) {

    suspend fun getBreakingNews(countryCode : String ,pageNo : Int) =
        RetrofitInstance.api.getNewsData(Constants.API_KEY,countryCode,pageNo)

    suspend fun searchNews(searchQuery : String,pageNo : Int): Response<NewsResponse> {
        return RetrofitInstance.api.getSearchData(Constants.API_KEY,searchQuery,pageNo)
    }

    suspend fun insertDB(article: Article): Long {
        return db.articleDoa().insert(article)
    }
    suspend fun deleteDB(article: Article) = db.articleDoa().delete(article)

    fun getAllDBNews() = db.articleDoa().getData()

}