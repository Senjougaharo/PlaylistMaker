package com.example.playlistmaker.search.domain

import com.example.playlistmaker.player.domain.model.Track

interface TrackSearchCallback {

    fun onSuccess(trackList: List<Track>)

    fun onError(message: String)
}