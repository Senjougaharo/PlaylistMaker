package com.example.playlistmaker.editPlaylist.presentation

import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.createPlaylist.data.entity.PlaylistEntity
import com.example.playlistmaker.createPlaylist.domain.PlaylistInteractor
import com.example.playlistmaker.createPlaylist.presentation.PlaylistCreateViewModel
import kotlinx.coroutines.launch

class EditPlaylistViewModel(private val interactor: PlaylistInteractor): PlaylistCreateViewModel(interactor) {

    fun saveUpdates(entity: PlaylistEntity){
        viewModelScope.launch {
            interactor.addPlaylist(entity)
        }
    }
}