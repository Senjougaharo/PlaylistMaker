package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.search.data.RemoteRepositoryImpl
import com.example.playlistmaker.search.data.SearchHistoryStorageImpl
import com.example.playlistmaker.search.data.retrofit
import com.example.playlistmaker.settings.data.ThemeInteractorImpl
import com.example.playlistmaker.search.data.SearchInteractorImpl

class MyCustomApplication : Application() {

    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val themeInteractor = ThemeInteractorImpl(sharedPreferences)
        val searchHistoryStorage = SearchHistoryStorageImpl(sharedPreferences)
        val remoteRepository = RemoteRepositoryImpl(retrofit)
        val searchInteractor = SearchInteractorImpl(remoteRepository, searchHistoryStorage)
        viewModelFactory = ViewModelFactory(themeInteractor, searchInteractor)
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



