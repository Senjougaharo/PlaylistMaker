package com.example.playlistmaker.createPlaylist.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.playlistmaker.Constants
import com.example.playlistmaker.media.domain.PlaylistModel

@Entity
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val cover: String,
    val trackList: String = "",
) {
    @Ignore
    val parsedTrackList = trackList.split(Constants.TRACK_LIST_SEPARATOR).filter { it.isNotBlank() }

    fun mapToUi(): PlaylistModel {
        return PlaylistModel(
            id = id,
            cover = cover,
            name = name,
            count = parsedTrackList.size,
            trackList = parsedTrackList
        )
    }
}