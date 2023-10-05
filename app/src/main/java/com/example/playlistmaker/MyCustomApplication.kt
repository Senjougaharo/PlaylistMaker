package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle

class MyCustomApplication : Application() {

        lateinit var searchHistory: SearchHistory
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        searchHistory = SearchHistory(sharedPreferences)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }






}



