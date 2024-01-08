package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.search.data.RemoteRepositoryImpl
import com.example.playlistmaker.search.data.SearchHistoryStorageImpl
import com.example.playlistmaker.search.data.retrofit
import com.example.playlistmaker.settings.data.ThemeInteractorImpl
import com.example.playlistmaker.search.domain.SearchHistoryStorage
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.settings.domain.ThemeInteractor

class MyCustomApplication : Application() {

        lateinit var searchHistoryStorage: SearchHistoryStorage
        lateinit var themeInteractor: ThemeInteractor
        lateinit var viewModelFactory : ViewModelFactory
    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        themeInteractor = ThemeInteractorImpl(sharedPreferences)
        val remoteRepository = RemoteRepositoryImpl(retrofit)
        val searchInteractor = SearchInteractor(remoteRepository)
        viewModelFactory = ViewModelFactory(themeInteractor, searchInteractor)
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



