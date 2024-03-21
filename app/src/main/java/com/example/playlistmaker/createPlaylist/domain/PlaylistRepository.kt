package com.example.playlistmaker.createPlaylist.domain

import androidx.lifecycle.LiveData
import com.example.playlistmaker.createPlaylist.domain.model.PlaylistDomainModel
import com.example.playlistmaker.media.domain.PlaylistModel
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.playlist.presentation.model.DetailedPlaylistModel
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun addPlaylist(playlist: PlaylistDomainModel)
    suspend fun addTrackToPlaylist(trackId: String, playlistId: Int)
    suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int)
    fun getPlaylists(): LiveData<List<PlaylistModel>>
    suspend fun addTrack(track: Track)
    fun getPlaylistById(id: Int): Flow<DetailedPlaylistModel>
    suspend fun deletePlaylist(playlist: DetailedPlaylistModel): Boolean
}