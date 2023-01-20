package com.mdasrafulalam.news.utils

import android.content.Context
import android.net.ConnectivityManager

class Constants {
    companion object{
//        58de75dc189f4cffb13c97b9bac00371
        val API_KEY = "2bd7895cc96e4a88bb0b58f85b4bca0d"
        var selectedTab = ""
        val categoryArray = arrayOf(
            "All News",
            "Business",
            "Entertainment",
            "Science",
            "Sports",
            "Technology",
            "Health"
        )
        var COUNTRY = "us"
        val CATEGORY_BUSINESS = "business"
        val CATEGORY_TOP_NEWS = "top_news"
        val CATEGORY_ENTERTAINMENT = "entertainment"
        val CATEGORY_HEALTH = "health"
        val CATEGORY_SCIENCE = "science"
        val CATEGORY_SPORTS = "sports"
        val CATEGORY_TECHNOLOGY = "technology"

        fun verifyAvailableNetwork(context: Context):Boolean{
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }
    }

}