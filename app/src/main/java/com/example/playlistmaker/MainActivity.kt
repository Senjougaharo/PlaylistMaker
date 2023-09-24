package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search_button = findViewById<Button>(R.id.search_button)

        search_button.setOnClickListener {

            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }


        val media_button = findViewById<Button>(R.id.media_button)

        media_button.setOnClickListener {

            val mediaIntent = Intent(this, MediaActivity::class.java)
            startActivity(mediaIntent)
        }
        val settings_button = findViewById<Button>(R.id.settings_button)

        settings_button.setOnClickListener{

            val settingsIntent = Intent(this, SettingsActivity::class.java)

            startActivity(settingsIntent)
        }






    }
}