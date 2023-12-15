package com.example.playlistmaker.data

import android.content.SharedPreferences

class ThemePrefs(val sharedPreferences: SharedPreferences) {
    
    fun saveTheme(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(THEME_KEY, isDarkMode).apply()
    }
    
    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(THEME_KEY, false)
    }
    
    companion object {
        
        const val THEME_KEY = "THEME_KEY"
    }
}