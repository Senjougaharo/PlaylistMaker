package com.example.playlistmaker.search.data

import com.example.playlistmaker.player.domain.model.Track

data class TrackSearchResponse(
    val resultCount: Int,
    val results: ArrayList<Track>
)