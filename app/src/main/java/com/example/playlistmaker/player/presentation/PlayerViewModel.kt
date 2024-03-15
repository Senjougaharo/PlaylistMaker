package com.example.playlistmaker.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.player.domain.PlayerInteractor
import com.example.playlistmaker.player.domain.model.Track
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val interactor: PlayerInteractor
) : ViewModel() {

    private val _isFavoriteLiveData = MutableLiveData<Boolean>()
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData

    fun addOrRemoveTrackFromFavorite(track: Track) {
        val isFavorite = _isFavoriteLiveData.value ?: false
        _isFavoriteLiveData.value = !isFavorite
        viewModelScope.launch {
            if (isFavorite) interactor.removeTrackFromFavorite(track.trackId)
            else interactor.addTrackToFavorite(track)
        }
    }

    fun checkIsFavorite(trackId: String) {
        viewModelScope.launch {
            val isFavorite = interactor.isTrackFavorite(trackId)
            _isFavoriteLiveData.value = isFavorite
        }
    }
}