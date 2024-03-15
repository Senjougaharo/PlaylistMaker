package com.example.playlistmaker.addToPlaylist.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playlistmaker.player.domain.model.Track

@Entity
data class PlaylistTrackDbEntity(
    val trackName: String,
    val artistName: String,
    val trackTime: Long,
    val artworkUrl: String,
    @PrimaryKey
    val trackId: String,
    val releaseDate: String,
    val country: String,
    val primaryGenreName: String,
    val collectionName: String,
    val previewUrl: String,
    var isFavorite: Boolean = false
) {
    fun mapToTrack(): Track {
        return Track(
            trackName,
            artistName,
            artworkUrl,
            trackTime,
            trackId,
            releaseDate,
            country,
            primaryGenreName,
            collectionName,
            previewUrl
        )
    }
}