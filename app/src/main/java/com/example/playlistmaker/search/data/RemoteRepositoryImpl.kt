package com.example.playlistmaker.search.data

import com.example.playlistmaker.data.db.FavoriteTracksDao
import com.example.playlistmaker.search.domain.RemoteRepository
import com.example.playlistmaker.search.domain.SearchState

class RemoteRepositoryImpl(
    private val trackApi: TrackAPI,
    private val favoriteTracksDao: FavoriteTracksDao
) : RemoteRepository {

    override suspend fun searchTrack(text: String): SearchState {
        val response = trackApi.getTrack(text)
        return if (response.isSuccessful) {
            val favoriteTrackIds = favoriteTracksDao.getFavoriteTrackIds()
            val body = response.body()
            if (body != null) {
                val resultList = body.results.map { it.copy(isFavorite = favoriteTrackIds.contains(it.trackId)) }
                SearchState.Success(resultList)
            } else SearchState.Error("Data is null")
        } else {
            SearchState.Error(response.message())
        }
    }
}