package com.example.playlistmaker.createPlaylist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.playlistmaker.Constants
import com.example.playlistmaker.addToPlaylist.data.PlaylistTrackDao
import com.example.playlistmaker.createPlaylist.data.entity.PlaylistEntity
import com.example.playlistmaker.createPlaylist.domain.PlaylistRepository
import com.example.playlistmaker.media.domain.PlaylistModel
import com.example.playlistmaker.player.domain.model.Track

class PlaylistRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val playlistTrackDao: PlaylistTrackDao
) : PlaylistRepository {

    override suspend fun addPlaylist(playlist: PlaylistEntity) {
        playlistDao.addPlaylist(playlist)
    }

    override suspend fun addTrackToPlaylist(trackId: String, playlistId: Int) {
        val json = playlistDao.getTrackListForPlaylist(playlistId)
        val list =
            json.split(Constants.TRACK_LIST_SEPARATOR).filter { it.isNotBlank() }.toMutableList()
        list.add(trackId)
        playlistDao.updateTrackList(playlistId, list.joinToString(Constants.TRACK_LIST_SEPARATOR))
    }

    override suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int) {
        val json = playlistDao.getTrackListForPlaylist(playlistId)
        val list = json.split(Constants.TRACK_LIST_SEPARATOR).toMutableList()
        list.remove(trackId)
        playlistDao.updateTrackList(playlistId, list.joinToString(Constants.TRACK_LIST_SEPARATOR))
        val playlists = playlistDao.getPlaylists()
        val isUsed = checkTrackUsage(trackId, playlists)
        if (!isUsed) {
            playlistTrackDao.deleteTrack(trackId)
        }
    }

    private fun checkTrackUsage(trackId: String, playlists: List<PlaylistEntity>): Boolean {
        var isUsed = false
        for (i in playlists) {
            if (i.parsedTrackList.contains(trackId)) {
                isUsed = true
                break
            }
        }
        return isUsed
    }

    override fun getPlaylists(): LiveData<List<PlaylistModel>> {
        return playlistDao.getPlaylistsLiveData().map { list -> list.map { it.mapToUi() } }
    }

    override suspend fun addTrack(track: Track) {
        playlistTrackDao.addTrack(track.toPlaylistDbModel())
    }
}