package com.example.playlistmaker.createPlaylist.domain

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.playlistmaker.createPlaylist.data.ImageSaver
import com.example.playlistmaker.createPlaylist.data.entity.PlaylistEntity
import com.example.playlistmaker.media.domain.PlaylistModel
import com.example.playlistmaker.player.domain.model.Track

class PlaylistInteractor(
    private val repository: PlaylistRepository,
    private val imageSaver: ImageSaver
) {
    suspend fun addPlaylist(playlist: PlaylistEntity) {
        repository.addPlaylist(playlist)
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
}