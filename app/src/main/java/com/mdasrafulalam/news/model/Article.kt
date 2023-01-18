package com.mdasrafulalam.news.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore


data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String,
    val urlToImage: String?
)