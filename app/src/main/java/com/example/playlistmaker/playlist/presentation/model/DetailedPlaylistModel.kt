package com.example.playlistmaker.playlist.presentation.model

import com.example.playlistmaker.player.domain.model.Track

data class DetailedPlaylistModel(
    val id: Int,
    val cover: String,
    val name: String,
    val description: String,
    val count: Int,
    val totalTime: Int,
    val trackList: List<Track>
)