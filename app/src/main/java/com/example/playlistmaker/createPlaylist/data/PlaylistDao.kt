package com.example.playlistmaker.createPlaylist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.playlistmaker.createPlaylist.data.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaylist(playlist: PlaylistEntity)
    @Query("DELETE FROM PlaylistEntity WHERE id = :id")
    suspend fun deletePlaylist(id: Int)

    @Query("SELECT trackList FROM PlaylistEntity WHERE id = :playlistId")
    suspend fun getTrackListForPlaylist(playlistId: Int):String

    @Query("UPDATE PlaylistEntity SET trackList = :trackList WHERE id = :playlistId")
    suspend fun updateTrackList(playlistId: Int, trackList:String)

    @Query("SELECT * FROM PlaylistEntity")
    fun getPlaylistsLiveData(): LiveData<List<PlaylistEntity>>

    @Query("SELECT * FROM PlaylistEntity WHERE id = :id")
    fun getPlaylistById(id: Int): Flow<PlaylistEntity>

    @Query("SELECT * FROM PlaylistEntity")
    suspend fun getPlaylists(): List<PlaylistEntity>

}