package com.example.playlistmaker.search.domain

import com.example.playlistmaker.player.domain.model.Track

interface SearchInteractor {

    suspend fun searchTracks(inputText: String): SearchState

    fun saveTrackToHistory(track: Track)

    fun readTrackHistory(): List<Track>
    fun clearHistory()
}