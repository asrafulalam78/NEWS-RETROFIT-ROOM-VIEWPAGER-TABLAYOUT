package com.mdasrafulalam.news

import android.app.Application
import android.content.Context

class MainApplication : Application() {
init { INSTANCE = this }

companion object {
        lateinit var INSTANCE: MainApplication
            private set
        val applicationContext: Context get() { return INSTANCE.applicationContext }
    }
}