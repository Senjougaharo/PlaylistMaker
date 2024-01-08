package com.example.playlistmaker.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.settings.domain.ThemePrefs

class SettingsViewModelFactory(private val themePrefs: ThemePrefs) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == SettingsViewModel::class.java)
            return SettingsViewModel(themePrefs) as T
        else
            throw IllegalStateException()
    }
}