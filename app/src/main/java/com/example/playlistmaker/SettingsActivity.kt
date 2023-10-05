package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val themePrefs = (application as MyCustomApplication).themePrefs
        val arrowBack = findViewById<ImageView>(R.id.arrowBack)

        arrowBack.setOnClickListener{
            finish()
        }

        val shareApp = findViewById<TextView>(R.id.share_app)

        shareApp.setOnClickListener {


            val url = getString(R.string.practicum)
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, url)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Поделиться ->"))

        }

        val supportRequest = findViewById<TextView>(R.id.support_request)

        supportRequest.setOnClickListener{

            val contact = getString(R.string.contact)
            val messageTheme = getString(R.string.message_theme_for_devs)
            val message = getString(R.string.message_for_devs)
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contact))
            intent.putExtra(Intent.EXTRA_SUBJECT, messageTheme)
            intent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(intent)

        }



        val userAgreement = findViewById<TextView>(R.id.user_agreement)

        userAgreement.setOnClickListener {

            val url = getString(R.string.practicum_offer)
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

        }
        
        val themeSwitcher = findViewById<SwitchCompat>(R.id.settings_dark_switch)
        themeSwitcher.isChecked = themePrefs.isDarkMode()
        themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
            (application as MyCustomApplication).switchTheme(isChecked)
            themePrefs.saveTheme(isChecked)
        }

    }
}