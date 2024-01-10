package com.example.playlistmaker.search.domain

import com.example.playlistmaker.player.domain.model.Track

interface SearchInteractor {

    fun searchTracks(inputText: String, callback: TrackSearchCallback)

    fun saveTrackToHistory(track: Track)

    fun readTrackHistory(): List<Track>
    fun clearHistory()
}