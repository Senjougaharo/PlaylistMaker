package com.example.playlistmaker.search.data

import com.example.playlistmaker.search.domain.RemoteRepository
import com.example.playlistmaker.search.domain.SearchState

class RemoteRepositoryImpl(private val trackApi: TrackAPI) : RemoteRepository {

    override suspend fun searchTrack(text: String): SearchState {
        val response = trackApi.getTrack(text)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null)
                 SearchState.Success(body.results)
            else SearchState.Error("Data is null")
        } else{
            SearchState.Error(response.message())
        }
    }
}