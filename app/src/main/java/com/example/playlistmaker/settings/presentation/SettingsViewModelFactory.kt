package com.example.playlistmaker.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.settings.domain.ThemeInteractor

class SettingsViewModelFactory(private val themeInteractor: ThemeInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == SettingsViewModel::class.java)
            return SettingsViewModel(themeInteractor) as T
        else
            throw IllegalStateException()
    }
}