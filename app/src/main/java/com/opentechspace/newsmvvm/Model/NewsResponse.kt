package com.opentechspace.newsmvvm.Model

import com.opentechspace.newsmvvm.Model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)