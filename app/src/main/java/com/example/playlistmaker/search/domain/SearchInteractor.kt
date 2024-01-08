package com.example.playlistmaker.search.domain

class SearchInteractor(private val remoteRepository: RemoteRepository) {

    fun searchTracks(inputText: String, callback: TrackSearchCallback) {
        remoteRepository.searchTrack(inputText, callback)
    }
}