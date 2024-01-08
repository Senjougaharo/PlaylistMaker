package com.example.playlistmaker.settings.data

import android.content.SharedPreferences
import com.example.playlistmaker.settings.domain.ThemePrefs

class ThemePrefsImpl(val sharedPreferences: SharedPreferences) : ThemePrefs {
    
    override fun saveTheme(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(THEME_KEY, isDarkMode).apply()
    }
    
    override fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(THEME_KEY, false)
    }
    
    companion object {
        
        const val THEME_KEY = "THEME_KEY"
    }
}