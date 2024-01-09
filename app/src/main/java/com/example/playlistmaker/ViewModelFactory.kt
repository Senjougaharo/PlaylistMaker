package com.example.playlistmaker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.search.data.SearchInteractorImpl
import com.example.playlistmaker.search.presentation.SearchViewModel
import com.example.playlistmaker.settings.domain.ThemeInteractor
import com.example.playlistmaker.settings.presentation.SettingsViewModel

class ViewModelFactory(private val themeInteractor: ThemeInteractor, private val searchInteractor: SearchInteractorImpl) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            SettingsViewModel::class.java -> SettingsViewModel(themeInteractor)
            SearchViewModel::class.java -> SearchViewModel(searchInteractor)

            else -> throw IllegalStateException()
        }
        return viewModel as T

    }
}