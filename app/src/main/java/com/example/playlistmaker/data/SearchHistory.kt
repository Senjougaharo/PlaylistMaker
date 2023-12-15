package com.example.playlistmaker.data

import android.content.SharedPreferences
import com.example.playlistmaker.domain.model.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistory(val sharedPreferences: SharedPreferences, val gson: Gson = Gson()) {
    
    
    val typeToken = object : TypeToken<ArrayList<Track>>() {}.type
    
    fun saveTrackToHistory(track: Track) {
        
        val trackListJson = sharedPreferences.getString(HISTORY_KEY, "")
        
        val trackList = gson.fromJson<ArrayList<Track>>(trackListJson, typeToken) ?: arrayListOf()
        trackList.remove(track)
        
        trackList.add(track)
        
        if (trackList.size > 10) {
            trackList.removeFirst()
        }
        
        sharedPreferences.edit().putString(HISTORY_KEY, gson.toJson(trackList))
            .apply()
    }
    
    fun readTrackHistory(): List<Track> {
        
        val trackListJson = sharedPreferences.getString(HISTORY_KEY, "")
        
        val trackList =
            gson.fromJson<ArrayList<Track>>(trackListJson, typeToken) ?: arrayListOf()
        
        return trackList.reversed()
        
    }
    
    fun clearHistory() {
        sharedPreferences.edit().clear().apply()
    }
    
    companion object {
        
        const val HISTORY_KEY = "HISTORY_KEY"
    }
}
