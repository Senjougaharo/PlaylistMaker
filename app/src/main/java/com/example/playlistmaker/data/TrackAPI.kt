package com.example.playlistmaker.data

import com.example.playlistmaker.domain.model.TrackSearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackAPI {

    @GET("/search?entity=song")
        fun getTrack(@Query("term")term:String) : Call<TrackSearchResponse>
}

val retrofit =  Retrofit.Builder()
    .baseUrl("https://itunes.apple.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(TrackAPI::class.java)