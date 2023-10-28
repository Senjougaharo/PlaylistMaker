package com.example.playlistmaker.model

import android.widget.TextView

data class Track(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String
)


