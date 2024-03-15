package com.example.playlistmaker.createPlaylist.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.createPlaylist.data.entity.PlaylistEntity
import com.example.playlistmaker.createPlaylist.domain.PlaylistInteractor
import kotlinx.coroutines.launch

open class PlaylistCreateViewModel(private val interactor: PlaylistInteractor): ViewModel() {
    fun addPlaylist(name: String, description: String, cover: String) {
        viewModelScope.launch {
            interactor.addPlaylist(PlaylistEntity(cover = cover, name = name, description = description))
        }
    }
    fun saveToInternal(uri: Uri, name: String): Uri {
        return interactor.saveToInternal(uri, name)
    }
}