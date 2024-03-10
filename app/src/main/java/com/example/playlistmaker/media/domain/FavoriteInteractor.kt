package com.example.playlistmaker.media.domain

import com.example.playlistmaker.player.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteInteractor {

    fun getAllFavoriteTracks() : Flow<List<Track>>
}