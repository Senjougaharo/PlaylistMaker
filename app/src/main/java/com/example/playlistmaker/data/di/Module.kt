package com.example.playlistmaker.data.di

import androidx.room.Room
import com.example.playlistmaker.data.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    factory {
        get<AppDatabase>().favoriteTracksDao()
    }
}