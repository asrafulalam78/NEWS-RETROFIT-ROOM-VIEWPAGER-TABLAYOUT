package com.mdasrafulalam.news.network

import com.mdasrafulalam.news.model.NewsModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopNews(@Query("country") q: String, @Query("apiKey") apiKey: String): NewsModel

    @GET("top-headlines")
    suspend fun getCategoryNews(
        @Query("country") q: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): NewsModel
}

object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}