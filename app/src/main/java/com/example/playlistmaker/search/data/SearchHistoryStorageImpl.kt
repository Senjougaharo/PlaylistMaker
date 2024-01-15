package com.example.playlistmaker.search.data

import android.content.SharedPreferences
import com.example.playlistmaker.search.domain.SearchHistoryStorage
import com.example.playlistmaker.player.domain.model.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistoryStorageImpl(val sharedPreferences: SharedPreferences, val gson: Gson) :
    SearchHistoryStorage {
    
    
    val typeToken = object : TypeToken<ArrayList<Track>>() {}.type
    
    override fun saveTrackToHistory(track: Track) {
        
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
    
    override fun readTrackHistory(): List<Track> {
        
        val trackListJson = sharedPreferences.getString(HISTORY_KEY, "")
        
        val trackList =
            gson.fromJson<ArrayList<Track>>(trackListJson, typeToken) ?: arrayListOf()
        
        return trackList.reversed()
        
    }
    
    override fun clearHistory() {
        sharedPreferences.edit().clear().apply()
    }
    
    companion object {
        
        const val HISTORY_KEY = "HISTORY_KEY"
    }
}
