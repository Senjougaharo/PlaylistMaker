package com.example.playlistmaker.player.domain

import com.example.playlistmaker.player.domain.model.Track

interface PlayerRepository {

    suspend fun addTracToFavorite(track: Track)

    suspend fun removeTrackFromFavorite(track: Track)
}