package com.example.playlistmaker.player.data

import com.example.playlistmaker.data.db.FavoriteTracksDao
import com.example.playlistmaker.player.domain.PlayerRepository
import com.example.playlistmaker.player.domain.model.Track

class PlayerRepositoryImpl(private val favoriteTracksDao: FavoriteTracksDao) : PlayerRepository {

    override suspend fun addTracToFavorite(track: Track) {
        favoriteTracksDao.addTrack(track.mapToEntity())
    }

    override suspend fun removeTrackFromFavorite(id: String) {
        favoriteTracksDao.deleteTrackById(id)
    }

    override suspend fun isTrackFavorite(trackId: String): Boolean {
        return favoriteTracksDao.getFavoriteTrackIds().contains(trackId)
    }
}