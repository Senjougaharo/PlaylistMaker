package com.example.playlistmaker.search.domain

interface RemoteRepository {

    suspend fun searchTrack(text: String): SearchState
}