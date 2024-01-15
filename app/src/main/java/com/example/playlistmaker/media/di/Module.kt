package com.example.playlistmaker.media.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.example.playlistmaker.media.presentation.PlaylistsViewModel
import com.example.playlistmaker.media.presentation.FavoriteTracksViewModel
import org.koin.dsl.module

val mediaModule = module {

    viewModelOf(::PlaylistsViewModel)

    viewModelOf(::FavoriteTracksViewModel)
}