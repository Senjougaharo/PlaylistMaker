package com.example.playlistmaker.settings.presentation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.settings.domain.ThemeInteractor

class SettingsViewModel(private val themeInteractor: ThemeInteractor) : ViewModel() {

    private val _liveData = MutableLiveData(themeInteractor.isDarkMode())

    val liveData: LiveData<Boolean> = _liveData

    fun createShareIntent(url : String): Intent {

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, url)
        intent.type = "text/plain"

        return intent
    }

    fun userAgreementIntent(url: String): Intent {


        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)

        return intent
    }

    fun supportRequestIntent(contact: String, messageTheme : String, message : String): Intent {

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contact))
        intent.putExtra(Intent.EXTRA_SUBJECT, messageTheme)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        return intent
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        themeInteractor.saveTheme(darkThemeEnabled)
        _liveData.value = darkThemeEnabled
    }
}