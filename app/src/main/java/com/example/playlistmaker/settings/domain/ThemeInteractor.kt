package com.example.playlistmaker.settings.domain

interface ThemeInteractor {

    fun saveTheme(isDarkMode: Boolean)

    fun isDarkMode(): Boolean
}