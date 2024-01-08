package com.example.playlistmaker.domain.model

data class TrackSearchResponse(
    val resultCount: Int,
    val results: ArrayList<Track>
)