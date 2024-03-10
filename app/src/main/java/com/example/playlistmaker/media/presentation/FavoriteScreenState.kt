package com.example.playlistmaker.media.presentation

import com.example.playlistmaker.player.domain.model.Track

sealed interface FavoriteScreenState{

    class Empty : FavoriteScreenState

    class NotEmpty(val list : List<Track>) : FavoriteScreenState

}