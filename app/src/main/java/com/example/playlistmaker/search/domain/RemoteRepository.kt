package com.example.playlistmaker.search.domain

interface RemoteRepository {

    fun searchTrack(text: String, callback: TrackSearchCallback)
}