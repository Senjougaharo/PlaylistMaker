package com.example.playlistmaker.player.domain

import com.example.playlistmaker.player.domain.model.Track

interface PlayerInteractor {

    suspend fun addTrackToFavorite(track: Track)

    suspend fun removeTrackFromFavorite(id: String)
}