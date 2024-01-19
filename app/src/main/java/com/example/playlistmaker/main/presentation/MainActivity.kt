package com.example.playlistmaker.main.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.media.presentation.MediaActivity
import com.example.playlistmaker.search.presentation.SearchActivity
import com.example.playlistmaker.settings.presentation.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.searchButton.setOnClickListener {

            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }



        binding.mediaButton.setOnClickListener {

            val mediaIntent = Intent(this, MediaActivity::class.java)
            startActivity(mediaIntent)
        }


        binding.settingsButton.setOnClickListener{

            val settingsIntent = Intent(this, SettingsActivity::class.java)

            startActivity(settingsIntent)
        }






    }
}