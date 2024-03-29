package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.addToPlaylist.di.playlistSmallModule
import com.example.playlistmaker.createPlaylist.di.playlistCreateModule
import com.example.playlistmaker.data.di.dataModule
import com.example.playlistmaker.editPlaylist.di.editPlaylistModule
import com.example.playlistmaker.media.di.mediaModule
import com.example.playlistmaker.player.di.playerModule
import com.example.playlistmaker.playlist.di.detailedPlaylistModule
import com.example.playlistmaker.search.di.searchModule
import com.example.playlistmaker.settings.di.settingsModule
import com.example.playlistmaker.settings.domain.ThemeInteractor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyCustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyCustomApplication)

            modules(searchModule, settingsModule, mediaModule, dataModule, playerModule, playlistSmallModule, playlistCreateModule, detailedPlaylistModule, editPlaylistModule)
        }

        val themeInteractor: ThemeInteractor by inject()
        val isDarkMode = themeInteractor.isDarkMode()
        switchTheme(isDarkMode)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

}



