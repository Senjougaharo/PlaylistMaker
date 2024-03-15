package com.example.playlistmaker.media.di

import com.example.playlistmaker.media.data.FavoriteRepositoryImpl
import com.example.playlistmaker.media.domain.FavoriteInteractor
import com.example.playlistmaker.media.domain.FavoriteInteractorImpl
import com.example.playlistmaker.media.domain.FavoriteRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.example.playlistmaker.media.presentation.PlaylistsViewModel
import com.example.playlistmaker.media.presentation.FavoriteTracksViewModel
import com.example.playlistmaker.player.domain.PlayerInteractor
import com.example.playlistmaker.player.domain.PlayerInteractorImpl
import com.example.playlistmaker.player.presentation.PlayerViewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val mediaModule = module {

    viewModelOf(::PlaylistsViewModel)

    viewModelOf(::FavoriteTracksViewModel)


    factory { FavoriteInteractorImpl(get()) } bind FavoriteInteractor::class

    factory { FavoriteRepositoryImpl(get()) } bind FavoriteRepository::class


}