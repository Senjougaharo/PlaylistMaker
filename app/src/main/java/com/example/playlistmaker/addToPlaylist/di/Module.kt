package com.example.playlistmaker.addToPlaylist.di

import com.example.playlistmaker.addToPlaylist.data.PlaylistTrackDao
import com.example.playlistmaker.addToPlaylist.presentation.AddToPlaylistViewModel
import com.example.playlistmaker.data.db.AppDatabase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val playlistSmallModule = module {
    factory {
        get<AppDatabase>().playlistTrackDao()
    } bind PlaylistTrackDao::class
    viewModelOf(::AddToPlaylistViewModel)
}