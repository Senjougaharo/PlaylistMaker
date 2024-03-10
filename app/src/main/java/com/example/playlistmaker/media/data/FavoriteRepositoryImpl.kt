package com.example.playlistmaker.media.data

import com.example.playlistmaker.data.db.FavoriteTracksDao
import com.example.playlistmaker.media.domain.FavoriteRepository
import com.example.playlistmaker.player.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val dao: FavoriteTracksDao
) : FavoriteRepository {
    override fun getAllFavoriteTracks(): Flow<List<Track>> {
        return dao.getAllTracks().map { list -> list.map { it.map() }.reversed() }
    }
}