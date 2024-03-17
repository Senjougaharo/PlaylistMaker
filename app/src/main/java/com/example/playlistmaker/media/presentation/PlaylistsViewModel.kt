package com.example.playlistmaker.media.presentation

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.createPlaylist.domain.PlaylistInteractor

class PlaylistsViewModel(interactor: PlaylistInteractor) :ViewModel() {

    val playlistsLiveData = interactor.getPlaylists()

}