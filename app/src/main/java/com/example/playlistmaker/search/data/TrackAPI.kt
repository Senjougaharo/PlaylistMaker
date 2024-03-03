package com.example.playlistmaker.search.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackAPI {

    @GET("/search?entity=song")
    suspend fun getTrack(@Query("term") term: String): Response<TrackSearchResponse>
}