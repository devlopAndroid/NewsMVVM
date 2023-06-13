package com.opentechspace.newsmvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.opentechspace.newsmvvm.Model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article) : Long

    @Query("SELECT * FROM News")
    fun getData() : LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}