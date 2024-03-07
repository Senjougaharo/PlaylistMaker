package com.example.playlistmaker.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.playlistmaker.data.db.entity.FavoriteTrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTracksDao {

    @Insert
    fun addTrack(trackEntity: FavoriteTrackEntity)

    @Delete
    fun deleteTrack(trackEntity: FavoriteTrackEntity)

    @Query("SELECT * FROM FavoriteTrackEntity")
    fun getAllTracks() : Flow<List<FavoriteTrackEntity>>

    @Query("SELECT trackId FROM FavoriteTrackEntity")
    fun getFavoriteTrackIds() : List<String>
}