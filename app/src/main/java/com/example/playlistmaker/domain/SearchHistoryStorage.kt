package com.example.playlistmaker.domain

import com.example.playlistmaker.domain.model.Track

interface SearchHistoryStorage {

    fun saveTrackToHistory(track: Track)

    fun readTrackHistory(): List<Track>

    fun clearHistory()
}