package com.example.playlistmaker.player.domain

import com.example.playlistmaker.player.domain.model.Track

class PlayerInteractorImpl(
    private val repository: PlayerRepository
): PlayerInteractor {

    override suspend fun addTrackToFavorite(track: Track) {
        repository.addTracToFavorite(track)
    }

    override suspend fun removeTrackFromFavorite(id: String) {
        repository.removeTrackFromFavorite(id)
    }

    override suspend fun isTrackFavorite(trackId: String): Boolean {
        return repository.isTrackFavorite(trackId)
    }
}