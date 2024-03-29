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
    suspend fun addTrack(trackEntity: FavoriteTrackEntity)

    @Query("DELETE FROM FavoriteTrackEntity WHERE trackId = :id")
    suspend fun deleteTrackById(id: String)

    @Query("SELECT * FROM FavoriteTrackEntity")
    fun getAllTracks(): Flow<List<FavoriteTrackEntity>>

    @Query("SELECT trackId FROM FavoriteTrackEntity")
    suspend fun getFavoriteTrackIds(): List<String>
}