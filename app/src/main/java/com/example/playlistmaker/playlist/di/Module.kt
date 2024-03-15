package com.example.playlistmaker.playlist.di

import com.example.playlistmaker.playlist.presentation.PlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val detailedPlaylistModule = module {
    viewModelOf(::PlaylistViewModel)
}