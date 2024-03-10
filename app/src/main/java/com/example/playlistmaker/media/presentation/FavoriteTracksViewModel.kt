package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.FavoriteInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteTracksViewModel(private val interactor: FavoriteInteractor) : ViewModel() {

    private  val _liveData = MutableLiveData<FavoriteScreenState>()

    val liveData : LiveData<FavoriteScreenState> = _liveData

    init {
        viewModelScope.launch {
            interactor.getAllFavoriteTracks().collect{
                if (it.isEmpty()){
                    _liveData.value = FavoriteScreenState.Empty()
                } else
                    _liveData.value = FavoriteScreenState.NotEmpty(it)
            }
        }
    }
}