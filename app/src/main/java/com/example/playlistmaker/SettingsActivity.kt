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

        val arrowBack = findViewById<ImageView>(R.id.arrowBack)

        arrowBack.setOnClickListener{

            val backIntent = Intent(this, MainActivity::class.java)

            startActivity(backIntent)
        }

        val share_app = findViewById<TextView>(R.id.share_app)

        share_app.setOnClickListener {


            val url = "https://practicum.yandex.ru/profile/android-developer/?from=catalog"
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, url)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Поделиться ->"))

        }

        val support_request = findViewById<TextView>(R.id.support_request)

        support_request.setOnClickListener{

            val contact = "semonsemonich@yandex.ru"
            val messageTheme = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
            val message = "Спасибо разработчикам и разработчицам за крутое приложение!"
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contact))
            intent.putExtra(Intent.EXTRA_SUBJECT, messageTheme)
            intent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(intent)

        }



        val user_agreement = findViewById<TextView>(R.id.user_agreement)

        user_agreement.setOnClickListener {

            val url = "https://yandex.ru/legal/practicum_offer/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

        }

    }
}