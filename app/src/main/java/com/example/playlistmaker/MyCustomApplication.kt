package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.data.SearchHistory
import com.example.playlistmaker.data.ThemePrefs

class MyCustomApplication : Application() {

        lateinit var searchHistory: SearchHistory
        lateinit var themePrefs: ThemePrefs
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        themePrefs = ThemePrefs(sharedPreferences)
        searchHistory = SearchHistory(sharedPreferences)
        val isDarkMode = themePrefs.isDarkMode()
        switchTheme(isDarkMode)
    }
    
    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

}



