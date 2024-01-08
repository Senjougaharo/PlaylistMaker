package com.example.playlistmaker.settings.domain

interface ThemePrefs {

    fun saveTheme(isDarkMode: Boolean)

    fun isDarkMode(): Boolean
}