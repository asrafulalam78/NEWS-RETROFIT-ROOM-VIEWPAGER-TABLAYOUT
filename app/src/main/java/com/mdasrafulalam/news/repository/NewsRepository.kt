package com.mdasrafulalam.news.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mdasrafulalam.news.dao.NewsDao
import com.mdasrafulalam.news.model.Article
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants
import kotlinx.coroutines.delay

class NewsRepository(private val dao:NewsDao)  {
    suspend fun addNews(news: News) = dao.addNews(news)
    suspend fun deleteNews(news: News) = dao.deleteNews(news)
    suspend fun updateNews(news: News) = dao.updateNews(news)
    fun getAllNews(category: String, country:String) : LiveData<List<News>>  =  dao.getAllNews(category,country)
    fun getBookMaredNews():LiveData<List<News>> = dao.getBookMaredNews()
    fun getNewsByCategory(category: String, country: String): LiveData<List<News>> = dao.getNewsByCategory(category, country)
    fun deleteNewsByCategory(category: String) = dao.deleteNewsByCategory(category)
}