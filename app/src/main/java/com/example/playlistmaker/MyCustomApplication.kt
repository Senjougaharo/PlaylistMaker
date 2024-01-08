package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.data.SearchHistoryStorageImpl
import com.example.playlistmaker.data.ThemePrefsImpl
import com.example.playlistmaker.domain.SearchHistoryStorage
import com.example.playlistmaker.domain.ThemePrefs

class MyCustomApplication : Application() {

        lateinit var searchHistoryStorage: SearchHistoryStorage
        lateinit var themePrefs: ThemePrefs
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        themePrefs = ThemePrefsImpl(sharedPreferences)
        searchHistoryStorage = SearchHistoryStorageImpl(sharedPreferences)
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



