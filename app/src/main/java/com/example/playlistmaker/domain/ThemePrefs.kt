package com.example.playlistmaker.domain

interface ThemePrefs {

    fun saveTheme(isDarkMode: Boolean)

    fun isDarkMode(): Boolean
}