package com.example.playlistmaker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playlistmaker.player.domain.model.Track

@Entity
data class FavoriteTrackEntity(
    @PrimaryKey
    val trackId: String,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val primaryGenreName: String,
    val releaseDate: String,
    val collectionName: String,
    val country: String,
    val previewUrl: String
) {
    fun map() = Track(
        trackId,
        trackName,
        artistName,
        trackTimeMillis,
        artworkUrl100,
        primaryGenreName,
        releaseDate,
        collectionName,
        country,
        previewUrl
    )
}