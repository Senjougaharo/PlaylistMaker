package com.example.playlistmaker.search.di

import android.app.Application
import android.content.SharedPreferences
import com.example.playlistmaker.search.data.RemoteRepositoryImpl
import com.example.playlistmaker.search.data.SearchHistoryStorageImpl
import com.example.playlistmaker.search.domain.SearchInteractorImpl
import com.example.playlistmaker.search.data.TrackAPI
import com.example.playlistmaker.search.domain.RemoteRepository
import com.example.playlistmaker.search.domain.SearchHistoryStorage
import com.example.playlistmaker.search.domain.SearchInteractor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.playlistmaker.search.presentation.SearchViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val searchModule = module {

    viewModelOf(::SearchViewModel)

    factory<SearchInteractor> {
        SearchInteractorImpl(get(), get())
    }

    factory<RemoteRepository> {
        RemoteRepositoryImpl(get(),get())
    }

    factory<TrackAPI> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
                .build())
            .build()
            .create(TrackAPI::class.java)
    }

    factory<SearchHistoryStorage> {
        SearchHistoryStorageImpl(get(), get())
    }

    single<SharedPreferences> {
        androidApplication().getSharedPreferences("MyPrefs", Application.MODE_PRIVATE)
    }

    factory { Gson() }

}