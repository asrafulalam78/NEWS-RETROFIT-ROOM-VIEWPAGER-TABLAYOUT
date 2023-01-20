package com.mdasrafulalam.news.model

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_news")
class News(
       @PrimaryKey(autoGenerate = true)
       @ColumnInfo(name = "id")
       val newsId: Int = 0,
       val author: String?,
       val content: String?,
       val description: String?,
       @ColumnInfo(name = "date_time")
       val publishedAt: String?,
       val title: String?,
       val url: String,
       @ColumnInfo(name = "image_url")
       @Nullable
       val urlToImage: String?,
       val category: String?,
       @ColumnInfo(name = "is_bookmared")
       var isBookmared: Boolean = false
) : java.io.Serializable