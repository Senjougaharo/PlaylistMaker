package com.example.playlistmaker.media.domain

data class PlaylistModel(
    val id: Int,
    val cover: String,
    val name: String,
    val count: Int,
    val trackList: List<String>
)