package com.mdasrafulalam.news.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mdasrafulalam.news.model.News

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(news: News) : Long

    @Update
    suspend fun updateNews(news: News)

    @Delete
    suspend fun deleteNews(news: News)

    @Query("select * from tbl_news")
    fun getAllNews() : LiveData<List<News>>

    @Query("select * from tbl_news where category = :category")
    fun getNewsByCategory(category: String) : LiveData<List<News>>

    @Query("select * from tbl_news where is_bookmared = 1")
    fun getBookMaredNews() : LiveData<List<News>>
}