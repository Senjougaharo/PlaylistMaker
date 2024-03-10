package com.example.playlistmaker.player.di

import com.example.playlistmaker.player.data.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.PlayerInteractor
import com.example.playlistmaker.player.domain.PlayerInteractorImpl
import com.example.playlistmaker.player.domain.PlayerRepository
import com.example.playlistmaker.player.presentation.PlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val playerModule = module {

    viewModelOf(::PlayerViewModel)

    factory { PlayerInteractorImpl(get()) } bind PlayerInteractor::class

    factory { PlayerRepositoryImpl(get()) } bind PlayerRepository::class



}