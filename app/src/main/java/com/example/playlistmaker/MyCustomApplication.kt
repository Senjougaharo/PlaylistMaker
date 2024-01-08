package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.search.data.SearchHistoryStorageImpl
import com.example.playlistmaker.settings.data.ThemePrefsImpl
import com.example.playlistmaker.search.domain.SearchHistoryStorage
import com.example.playlistmaker.settings.domain.ThemePrefs
import com.example.playlistmaker.settings.presentation.SettingsViewModelFactory

class MyCustomApplication : Application() {

        lateinit var searchHistoryStorage: SearchHistoryStorage
        lateinit var themePrefs: ThemePrefs
        lateinit var settingsViewModelFactory : SettingsViewModelFactory
    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        themePrefs = ThemePrefsImpl(sharedPreferences)
        settingsViewModelFactory = SettingsViewModelFactory(themePrefs)
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



