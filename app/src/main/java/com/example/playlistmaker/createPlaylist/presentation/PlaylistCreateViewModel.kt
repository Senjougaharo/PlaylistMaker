package com.example.playlistmaker.createPlaylist.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.createPlaylist.domain.PlaylistInteractor
import com.example.playlistmaker.createPlaylist.domain.model.PlaylistDomainModel
import kotlinx.coroutines.launch

open class PlaylistCreateViewModel(private val interactor: PlaylistInteractor) : ViewModel() {
    fun addPlaylist(name: String, description: String, cover: String) {
        viewModelScope.launch {
            interactor.addPlaylist(name = name, description = description, cover = cover)
        }
    }

    fun saveToInternal(uri: Uri, name: String): Uri {
        return interactor.saveToInternal(uri, name)
    }
}