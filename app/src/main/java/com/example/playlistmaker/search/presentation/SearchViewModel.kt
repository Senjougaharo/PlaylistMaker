package com.example.playlistmaker.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.domain.SearchState
import kotlinx.coroutines.launch

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val _liveData = MutableLiveData<SearchState>()
    val liveData: LiveData<SearchState> = _liveData

    fun searchTracks(inputText: String) {
        viewModelScope.launch {
            val result = interactor.searchTracks(inputText)
            _liveData.value = result
        }
    }

    fun saveTrackToHistory(track: Track) {
        interactor.saveTrackToHistory(track)
    }

    fun readTrackHistory(): List<Track> {
        return interactor.readTrackHistory()
    }

    fun clearHistory() {
        interactor.clearHistory()
    }
}