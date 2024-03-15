package com.example.playlistmaker.createPlaylist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.playlistmaker.Constants
import com.example.playlistmaker.addToPlaylist.data.PlaylistTrackDao
import com.example.playlistmaker.createPlaylist.data.entity.PlaylistEntity
import com.example.playlistmaker.createPlaylist.domain.PlaylistRepository
import com.example.playlistmaker.media.domain.PlaylistModel
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.playlist.presentation.model.DetailedPlaylistModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull

class PlaylistRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val playlistTrackDao: PlaylistTrackDao
) : PlaylistRepository {

    override suspend fun addPlaylist(playlist: PlaylistEntity) {
        playlistDao.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: DetailedPlaylistModel): Boolean {
        playlistDao.deletePlaylist(playlist.id)
        val playlists = playlistDao.getPlaylists()
        playlist.trackList.forEach {
            val isUsed = checkTrackUsage(it.trackId, playlists)
            if (!isUsed) playlistTrackDao.deleteTrack(it.trackId)
        }
        return true
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

    override fun getPlaylistById(id: Int): Flow<DetailedPlaylistModel> {
        val playlistFlow = playlistDao.getPlaylistById(id)
        val tracksFlow = playlistTrackDao.getAllTracks()
        return playlistFlow
            .filterNotNull()
            .combine(tracksFlow) { playlist, trackList ->
                val filteredTrackList =
                    trackList.filter { playlist.parsedTrackList.contains(it.trackId) }
                        .map { it.mapToTrack() }
                DetailedPlaylistModel(
                    playlist.id,
                    playlist.cover,
                    playlist.name,
                    playlist.description,
                    playlist.parsedTrackList.size,
                    (filteredTrackList.sumOf { it.trackTimeMillis } / 60000).toInt(),
                    filteredTrackList.reversed()
                )
            }
    }

}