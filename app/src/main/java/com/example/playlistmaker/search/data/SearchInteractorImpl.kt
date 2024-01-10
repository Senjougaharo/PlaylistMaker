package com.example.playlistmaker.search.data

import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.search.domain.RemoteRepository
import com.example.playlistmaker.search.domain.SearchHistoryStorage
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.domain.TrackSearchCallback

class SearchInteractorImpl(
    private val remoteRepository: RemoteRepository,
    private val searchHistoryStorage: SearchHistoryStorage
) : SearchInteractor {

    override fun searchTracks(inputText: String, callback: TrackSearchCallback) {
        remoteRepository.searchTrack(inputText, callback)
    }

    override fun saveTrackToHistory(track: Track) {
        searchHistoryStorage.saveTrackToHistory(track)
    }

    override fun readTrackHistory(): List<Track> {
        return searchHistoryStorage.readTrackHistory()
    }

    override fun clearHistory() {
        searchHistoryStorage.clearHistory()
    }
}