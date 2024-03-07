package com.example.playlistmaker.search.domain

import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.search.domain.RemoteRepository
import com.example.playlistmaker.search.domain.SearchHistoryStorage
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.domain.SearchState

class SearchInteractorImpl(
    private val remoteRepository: RemoteRepository,
    private val searchHistoryStorage: SearchHistoryStorage
) : SearchInteractor {

    override suspend fun searchTracks(inputText: String): SearchState {
        return remoteRepository.searchTrack(inputText)
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