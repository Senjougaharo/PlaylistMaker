package com.example.playlistmaker.search.domain

import com.example.playlistmaker.player.domain.model.Track

sealed class SearchState{
    class Success(val trackList: List<Track>): SearchState()

    class Error(val message: String): SearchState()
}
