package com.mdasrafulalam.news.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData

class Constants {
    companion object{

        val countryList = listOf("United States of America", "Argentina", " Ashmore and Cartier Islands",
            "Austria", "Belgium", "Bulgeria", "Brazil", "Canada","China", "Comoros", "Colombia","Cuba","Czech Republic","Germany","Egypt","France","Great Britain","Greece","Hong Kong",
            "Hungary", "Indonesia","Ireland","Israel","India","Italy","Japan","Korea","Lithuania","Latvia","Madagascar","Mexico","Malaysia","Niger","Netherlands","Norway","New Zealand","Philippines","Poland","Portugal",
            "Romania","Russia","Russian Federation","Saudi Arabia","Seychelles","Singapore","Slovenia","Slovakia","Thailand","Turkey","Taiwan","Ukraine","UAE","Venezuela","Zambia")
        val countryCode = listOf("us", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk",
            "hu", "id", "ie", "il", "in", "it", "jp", "kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt",
            "ro","rs","ru","sa","se","sg","si","sk","th","tr","tw","ua","ae","ve","za")
        //                       327ca3e10138432280512f1a94f3eaed "2bd7895cc96e4a88bb0b58f85b4bca0d"
        val API_KEY =  "58de75dc189f4cffb13c97b9bac00371"
        var selectedTab = ""
        val categoryArray = arrayOf(
            "Top News",
            "Business",
            "Entertainment",
            "Science",
            "Sports",
            "Technology",
            "Health"
        )
        // Name of Notification Channel for verbose notifications of background work
        @JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
            "News Notifications"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts"
        @JvmField val NOTIFICATION_TITLE: CharSequence = "News Updated"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1

        var COUNTRY = MutableLiveData<String>()
        var DARKMODE = MutableLiveData<Boolean>()
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