package com.example.playlistmaker

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.model.Track
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerActivity : AppCompatActivity() {

    private val mediaPlayer = MediaPlayer()

    private val mainHandler = Handler(Looper.getMainLooper())

    private var counter = 0L

    private val runnable = object : Runnable{
        override fun run() {
            playTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(counter)

            counter +=DELAY

            mainHandler.postDelayed(this, DELAY)
        }


    }

    private val playTime by lazy {findViewById<TextView>(R.id.playTime)}

    val trackCover by lazy {findViewById<ImageView>(R.id.trackCover)}

    val trackName by lazy {findViewById<TextView>(R.id.trackName)}

    val trackArtist by lazy {findViewById<TextView>(R.id.trackArtist)}

    val duration by lazy {findViewById<TextView>(R.id.durationValue)}

    val album by lazy {findViewById<TextView>(R.id.albumValue)}

    val year by lazy {findViewById<TextView>(R.id.yearValue)}

    val genre by lazy {findViewById<TextView>(R.id.genreValue)}

    val country by lazy {findViewById<TextView>(R.id.countryValue)}


    private var currentState = STATE_DEFAULT

    private val playButton by lazy {findViewById<ImageView>(R.id.playButton)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val track = intent.getSerializableExtra("track") as Track

        preparePlayer(track.previewUrl)

        val arrowBack = findViewById<ImageView>(R.id.arrowBack)

        arrowBack.setOnClickListener{
            finish()
        }

        playButton.setOnClickListener{
            playBackControl()

        }


        trackName.text = track.trackName

        trackArtist.text = track.artistName

        duration.text = track.timeFormat()

        album.text = track.collectionName

        year.text = track.releaseDate.take(4)

        genre.text = track.primaryGenreName

        country.text = track.country

        Glide.with(this)
            .load(track.getCoverArtwork())
            .transform(
            CenterInside(),
            RoundedCorners(resources.getDimensionPixelSize(R.dimen.player_track_cover_corner_radius)))
            .placeholder(R.drawable.placeholder)
            .into(trackCover)

    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun preparePlayer(previewUrl: String){
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            currentState = STATE_PREPARED

        }
        mediaPlayer.setOnCompletionListener {
            playButton.setImageResource(R.drawable.play_button)
            mainHandler.removeCallbacks(runnable)
            counter = 0
            playTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(counter)
            currentState = STATE_PREPARED
        }

    }

    private fun startPlayer(){
        mediaPlayer.start()
        currentState = STATE_PLAYING
        playButton.setImageResource(R.drawable.pause)
        mainHandler.post(runnable)

    }

    private fun pausePlayer(){
        mediaPlayer.pause()
        currentState = STATE_PAUSED
        playButton.setImageResource(R.drawable.play_button)
        mainHandler.removeCallbacks(runnable)
    }

    private fun playBackControl(){
        when(currentState){
            STATE_PLAYING -> pausePlayer()
            STATE_PREPARED, STATE_PAUSED -> startPlayer()
        }
    }

    companion object {
        const val STATE_DEFAULT = 0

        const val STATE_PREPARED = 1

        const val STATE_PLAYING = 2

        const val STATE_PAUSED = 3

        const val DELAY = 1000L
    }


}