package com.example.playlistmaker.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.domain.SearchState
import com.example.playlistmaker.search.domain.TrackSearchCallback

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val _liveData = MutableLiveData<SearchState>()
    val liveData : LiveData<SearchState> = _liveData

    fun searchTracks(inputText: String){
        val callback = object : TrackSearchCallback {
            override fun onSuccess(trackList: List<Track>) {
                _liveData.value = SearchState.Success(trackList)
            }

            override fun onError(message: String) {
                _liveData.value = SearchState.Error(message)
            }
        }
        interactor.searchTracks(inputText, callback)
    }
}