package com.example.playlistmaker.editPlaylist.di


import com.example.playlistmaker.editPlaylist.presentation.EditPlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val editPlaylistModule = module {
    viewModelOf(::EditPlaylistViewModel)
}