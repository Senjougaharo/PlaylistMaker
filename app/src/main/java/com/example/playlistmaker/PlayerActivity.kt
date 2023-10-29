package com.example.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.playlistmaker.model.Track


class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val arrowBack = findViewById<ImageView>(R.id.arrowBack)

        arrowBack.setOnClickListener{
            finish()
        }

        val track = intent.getSerializableExtra("track") as Track

        val trackCover = findViewById<ImageView>(R.id.trackCover)

        val trackName = findViewById<TextView>(R.id.trackName)

        val trackArtist = findViewById<TextView>(R.id.trackArtist)

        val duration = findViewById<TextView>(R.id.durationValue)

        val album = findViewById<TextView>(R.id.albumValue)

        val year = findViewById<TextView>(R.id.yearValue)

        val genre = findViewById<TextView>(R.id.genreValue)

        val country = findViewById<TextView>(R.id.countryValue)

        trackName.text = track.trackName

        trackArtist.text = track.artistName

        duration.text = track.timeFormat()

        album.text = track.collectionName

        year.text = track.releaseDate.take(4)

        genre.text = track.primaryGenreName

        country.text = track.country

        Glide.with(this)
            .load(track.getCoverArtwork())
            .placeholder(R.drawable.placeholder)
            .into(trackCover)

    }


}