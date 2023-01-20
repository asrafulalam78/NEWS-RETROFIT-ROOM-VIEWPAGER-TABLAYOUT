package com.mdasrafulalam.news.repository

import androidx.lifecycle.LiveData
import com.mdasrafulalam.news.dao.NewsDao
import com.mdasrafulalam.news.model.Article
import com.mdasrafulalam.news.model.News

class NewsRepository(private val dao:NewsDao) {
    suspend fun addNews(news: News) = dao.addNews(news)

    suspend fun deleteNews(news: News) = dao.deleteNews(news)

    suspend fun updateNews(news: News) = dao.updateNews(news)

    fun getAllNews() : LiveData<List<News>> = dao.getAllNews()

    fun getBookMaredNews():LiveData<List<News>> = dao.getBookMaredNews()

    fun getNewsByCategory(category: String): LiveData<List<News>> = dao.getNewsByCategory(category)
}