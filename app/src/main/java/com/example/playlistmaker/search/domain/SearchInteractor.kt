package com.example.playlistmaker.search.domain

import com.example.playlistmaker.player.domain.model.Track

class SearchInteractor(
    private val remoteRepository: RemoteRepository,
    private val searchHistoryStorage: SearchHistoryStorage
) {

    fun searchTracks(inputText: String, callback: TrackSearchCallback) {
        remoteRepository.searchTrack(inputText, callback)
    }

    fun saveTrackToHistory(track: Track) {
        searchHistoryStorage.saveTrackToHistory(track)
    }

    fun readTrackHistory(): List<Track> {
        return searchHistoryStorage.readTrackHistory()
    }

    fun clearHistory() {
        searchHistoryStorage.clearHistory()
    }
}