package com.example.playlistmaker.createPlaylist.domain

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.playlistmaker.createPlaylist.data.ImageSaver
import com.example.playlistmaker.createPlaylist.domain.model.PlaylistDomainModel
import com.example.playlistmaker.media.domain.PlaylistModel
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.playlist.presentation.model.DetailedPlaylistModel
import kotlinx.coroutines.flow.Flow

class PlaylistInteractor(
    private val repository: PlaylistRepository,
    private val imageSaver: ImageSaver
) {
    suspend fun addPlaylist(id: Int = 0, name: String, description: String, cover: String, trackList: String = "") {
        repository.addPlaylist(PlaylistDomainModel(id = id, cover = cover, name = name, description = description, trackList = trackList))
    }

    fun getPlaylists(): LiveData<List<PlaylistModel>> {
        return repository.getPlaylists()
    }
    suspend fun addTrack(track: Track, playlistId: Int) {
        repository.addTrack(track)
        repository.addTrackToPlaylist(track.trackId, playlistId)
    }
    fun saveToInternal(uri: Uri, name: String): Uri {
        return imageSaver.saveToInternal(uri, name)
    }

    fun getPlaylistById(id: Int): Flow<DetailedPlaylistModel> {
        return repository.getPlaylistById(id)
    }

    suspend fun deletePlaylist(playlist: DetailedPlaylistModel): Boolean {
        return repository.deletePlaylist(playlist)
    }

    suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int) {
        repository.deleteTrackFromPlaylist(trackId,playlistId)
    }
}