package com.opentechspace.newsmvvm.Api

import com.opentechspace.newsmvvm.Model.NewsResponse
import com.opentechspace.newsmvvm.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("v2/top-headlines")
    suspend fun getNewsData(
        @Query("apiKey") apikey : String = API_KEY,
        @Query("country") country : String = "in",
        @Query("page") page : Int = 1
    ) : Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getSearchData(
        @Query("apiKey") apikey : String = API_KEY,
        @Query("q") searchQuery : String,
        @Query("page") page : Int = 1
    ) : Response<NewsResponse>
}