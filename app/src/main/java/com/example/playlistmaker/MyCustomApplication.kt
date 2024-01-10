package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.search.data.RemoteRepositoryImpl
import com.example.playlistmaker.search.data.SearchHistoryStorageImpl
import com.example.playlistmaker.search.data.retrofit
import com.example.playlistmaker.settings.data.ThemeInteractorImpl
import com.example.playlistmaker.search.data.SearchInteractorImpl
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.settings.domain.ThemeInteractor

class MyCustomApplication : Application() {

    lateinit var searchInteractor: SearchInteractor
    lateinit var themeInteractor: ThemeInteractor
    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        themeInteractor = ThemeInteractorImpl(sharedPreferences)
        val searchHistoryStorage = SearchHistoryStorageImpl(sharedPreferences)
        val remoteRepository = RemoteRepositoryImpl(retrofit)
        searchInteractor = SearchInteractorImpl(remoteRepository, searchHistoryStorage)
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



