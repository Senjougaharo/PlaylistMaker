package com.example.playlistmaker.settings.di

import com.example.playlistmaker.settings.data.ThemeInteractorImpl
import com.example.playlistmaker.settings.domain.ThemeInteractor
import com.example.playlistmaker.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {

    viewModelOf(::SettingsViewModel)

    factory<ThemeInteractor>{

        ThemeInteractorImpl(get())
    }
}