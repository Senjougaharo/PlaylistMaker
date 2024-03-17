package com.example.playlistmaker.createPlaylist.domain

import androidx.lifecycle.LiveData
import com.example.playlistmaker.createPlaylist.data.entity.PlaylistEntity
import com.example.playlistmaker.media.domain.PlaylistModel
import com.example.playlistmaker.player.domain.model.Track

interface PlaylistRepository {
    suspend fun addPlaylist(playlist: PlaylistEntity)
    suspend fun addTrackToPlaylist(trackId: String, playlistId: Int)
    suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int)
    fun getPlaylists(): LiveData<List<PlaylistModel>>
    suspend fun addTrack(track: Track)
}