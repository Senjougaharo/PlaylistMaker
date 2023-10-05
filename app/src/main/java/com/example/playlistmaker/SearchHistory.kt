package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.playlistmaker.model.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistory(val sharedPreferences: SharedPreferences, val gson: Gson = Gson()) {



    val typeToken = object: TypeToken<ArrayList<Track>>(){}.type

    fun saveTrackToHistory(track: Track){

        val trackListJson = sharedPreferences.getString(HISTORY_KEY, "")

        val trackList = gson.fromJson<ArrayList<Track>>(trackListJson, typeToken)?: arrayListOf()

        if (trackList.contains(track)){
            trackList.remove(track)
        }

        trackList.add(track)

        if(trackList.size > 10){
            trackList.removeFirst()
        }

        sharedPreferences.edit().putString(HISTORY_KEY, gson.toJson(trackList))
            .apply()
    }

    fun readTrackHistory(): ArrayList<Track> {

        val trackListJson = sharedPreferences.getString(HISTORY_KEY, "")

        val trackList = gson.fromJson<ArrayList<Track>>(trackListJson, typeToken)?: arrayListOf()

        return trackList

    }



    companion object {

        const val HISTORY_KEY = "HISTORY_KEY"}
}
