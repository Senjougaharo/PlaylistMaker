package com.example.playlistmaker.settings.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.MyCustomApplication
import com.example.playlistmaker.R
import com.example.playlistmaker.ViewModelFactory

class SettingsActivity  : AppCompatActivity() {

    private lateinit var viewModel : SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val app = application as MyCustomApplication
        val viewModelFactory = ViewModelFactory(app.themeInteractor, app.searchInteractor)
        val themeSwitcher = findViewById<SwitchCompat>(R.id.settings_dark_switch)
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]
        viewModel.liveData.observe(this){

            themeSwitcher.isChecked = it
        }

        val arrowBack = findViewById<ImageView>(R.id.arrowBack)

        arrowBack.setOnClickListener{
            finish()
        }

        val shareApp = findViewById<TextView>(R.id.share_app)
        shareApp.setOnClickListener {

            val url = getString(R.string.practicum)
            val intent = viewModel.createShareIntent(url)
            startActivity(Intent.createChooser(intent, "Поделиться ->"))

        }

        val supportRequest = findViewById<TextView>(R.id.support_request)
        supportRequest.setOnClickListener{


            val contact = getString(R.string.contact)
            val messageTheme = getString(R.string.message_theme_for_devs)
            val message = getString(R.string.message_for_devs)
            val intent = viewModel.supportRequestIntent(contact, messageTheme, message)
            startActivity(intent)

        }



        val userAgreement = findViewById<TextView>(R.id.user_agreement)
        userAgreement.setOnClickListener {

            val url = getString(R.string.practicum_offer)
            val intent = viewModel.userAgreementIntent(url)
            startActivity(intent)

        }
        
            themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
            viewModel.switchTheme(isChecked)

        }

    }
}