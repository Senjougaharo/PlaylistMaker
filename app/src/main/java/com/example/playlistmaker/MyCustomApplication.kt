package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.search.data.SearchHistoryStorageImpl
import com.example.playlistmaker.settings.data.ThemeInteractorImpl
import com.example.playlistmaker.search.domain.SearchHistoryStorage
import com.example.playlistmaker.settings.domain.ThemeInteractor
import com.example.playlistmaker.settings.presentation.SettingsViewModelFactory

class MyCustomApplication : Application() {

        lateinit var searchHistoryStorage: SearchHistoryStorage
        lateinit var themeInteractor: ThemeInteractor
        lateinit var settingsViewModelFactory : SettingsViewModelFactory
    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        themeInteractor = ThemeInteractorImpl(sharedPreferences)
        settingsViewModelFactory = SettingsViewModelFactory(themeInteractor)
        searchHistoryStorage = SearchHistoryStorageImpl(sharedPreferences)
        val isDarkMode = themeInteractor.isDarkMode()
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



