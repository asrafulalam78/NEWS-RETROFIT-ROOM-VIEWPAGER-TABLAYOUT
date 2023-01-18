package com.mdasrafulalam.news.repository

import androidx.lifecycle.LiveData
import com.mdasrafulalam.news.dao.NewsDao
import com.mdasrafulalam.news.model.Article
import com.mdasrafulalam.news.model.News

class NewsRepository(private val dao:NewsDao) {
    suspend fun addNews(news: News) : Long = dao.addNews(news)

    suspend fun deleteNews(news: News) = dao.deleteNews(news)

    fun getAllNews() : LiveData<List<News>> = dao.getAllNews()

    fun getNewsByCategory(category: String): LiveData<List<News>> = dao.getNewsByCategory(category)
}