package com.example.playlistmaker.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.search_button)

        searchButton.setOnClickListener {

            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }


        val mediaButton = findViewById<Button>(R.id.media_button)

        mediaButton.setOnClickListener {

            val mediaIntent = Intent(this, MediaActivity::class.java)
            startActivity(mediaIntent)
        }
        val settingsButton = findViewById<Button>(R.id.settings_button)

        settingsButton.setOnClickListener{

            val settingsIntent = Intent(this, SettingsActivity::class.java)

            startActivity(settingsIntent)
        }






    }
}