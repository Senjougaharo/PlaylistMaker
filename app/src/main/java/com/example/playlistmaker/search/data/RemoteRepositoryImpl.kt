package com.example.playlistmaker.search.data

import com.example.playlistmaker.search.domain.RemoteRepository
import com.example.playlistmaker.search.domain.TrackSearchCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepositoryImpl(private val trackApi: TrackAPI) : RemoteRepository {

    override fun searchTrack(text: String, callback: TrackSearchCallback) {
        trackApi.getTrack(text).enqueue(object : Callback<TrackSearchResponse> {
            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                val body = response.body()
                if (body == null){
                    callback.onError("Data is null")
                } else{
                    callback.onSuccess(body.results)
                }
            }

            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
                callback.onError(t.message ?: "Unknown error")
            }
        })
    }
}