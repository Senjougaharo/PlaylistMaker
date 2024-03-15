package com.example.playlistmaker.createPlaylist.di

import com.example.playlistmaker.createPlaylist.data.ImageSaver
import com.example.playlistmaker.createPlaylist.data.PlaylistDao
import com.example.playlistmaker.createPlaylist.data.PlaylistRepositoryImpl
import com.example.playlistmaker.createPlaylist.domain.PlaylistInteractor
import com.example.playlistmaker.createPlaylist.domain.PlaylistRepository
import com.example.playlistmaker.createPlaylist.presentation.PlaylistCreateViewModel
import com.example.playlistmaker.data.db.AppDatabase
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val playlistCreateModule = module {
    factory {
        ImageSaver(androidApplication())
    }
    factory {
        PlaylistInteractor(get(), get())
    }
    factory {
        PlaylistRepositoryImpl(get(),get())
    } bind PlaylistRepository::class
    factory {
        get<AppDatabase>().playlistDao()
    } bind PlaylistDao::class
    factory {
        Gson()
    }
    viewModelOf(::PlaylistCreateViewModel)
}