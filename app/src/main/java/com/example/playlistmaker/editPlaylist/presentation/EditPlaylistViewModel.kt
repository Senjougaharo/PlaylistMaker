package com.example.playlistmaker.editPlaylist.presentation

import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.createPlaylist.data.entity.PlaylistEntity
import com.example.playlistmaker.createPlaylist.domain.PlaylistInteractor
import com.example.playlistmaker.createPlaylist.presentation.PlaylistCreateViewModel
import kotlinx.coroutines.launch

class EditPlaylistViewModel(private val interactor: PlaylistInteractor): PlaylistCreateViewModel(interactor) {

    fun saveUpdates(id: Int, name: String, description: String, cover: String, trackList: String){
        viewModelScope.launch {
            interactor.addPlaylist(id, name, description, cover, trackList)
        }
    }
}