package com.example.playlistmaker

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle

class MyCustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }






}



