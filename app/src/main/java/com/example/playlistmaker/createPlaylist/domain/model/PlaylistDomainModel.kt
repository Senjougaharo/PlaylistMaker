package com.example.playlistmaker.createPlaylist.domain.model

import com.example.playlistmaker.createPlaylist.data.entity.PlaylistEntity

class PlaylistDomainModel(
    val id: Int = 0,
    val name: String,
    val description: String,
    val cover: String,
    val trackList: String = "",
) {

    fun mapToEntity() = PlaylistEntity(
        id, name, description, cover
    )
}