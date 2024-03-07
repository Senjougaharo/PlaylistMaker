package com.example.playlistmaker.player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.player.domain.PlayerInteractor
import com.example.playlistmaker.player.domain.model.Track
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val interactor: PlayerInteractor
) : ViewModel() {


    fun addTrackToFavorite(track: Track) {
        viewModelScope.launch {
            interactor.addTrackToFavorite(track)
        }
    }

    fun removeTrackFromFavorite(track: Track) {
        viewModelScope.launch {
            interactor.removeTrackFromFavorite(track)
        }
    }
}