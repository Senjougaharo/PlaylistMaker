package com.example.playlistmaker.player.domain.model


import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.example.playlistmaker.addToPlaylist.data.entity.PlaylistTrackDbEntity
import com.example.playlistmaker.data.db.entity.FavoriteTrackEntity
import kotlinx.parcelize.Parcelize
import java.io.Serial
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class Track(
    val trackId: String,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val primaryGenreName: String? = "",
    val releaseDate: String,
    val collectionName: String,
    val country: String,
    val previewUrl: String,
    val isFavorite: Boolean = false
) : Parcelable {
    fun getCoverArtwork() = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")

    fun timeFormat() = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)

    fun mapToEntity() = FavoriteTrackEntity(
        trackId,
        trackName,
        artistName,
        trackTimeMillis,
        artworkUrl100,
        primaryGenreName ?: "",
        releaseDate,
        collectionName,
        country,
        previewUrl
    )

    fun toPlaylistDbModel() = PlaylistTrackDbEntity(
        trackName,
        artistName,
        trackTimeMillis,
        artworkUrl100,
        trackId,
        releaseDate,
        country,
        primaryGenreName ?: "",
        collectionName,
        previewUrl
    )
}



