package com.example.playlistmaker.player.presentation

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.player.domain.model.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerActivity : AppCompatActivity() {

    private val mediaPlayer = MediaPlayer()

    private var job: Job? = null

    private val playTime by lazy { findViewById<TextView>(R.id.playTime) }

    private val trackCover by lazy { findViewById<ImageView>(R.id.trackCover) }

    private val trackName by lazy { findViewById<TextView>(R.id.trackName) }

    private val trackArtist by lazy { findViewById<TextView>(R.id.trackArtist) }

    private val duration by lazy { findViewById<TextView>(R.id.durationValue) }

    private val album by lazy { findViewById<TextView>(R.id.albumValue) }

    private val year by lazy { findViewById<TextView>(R.id.yearValue) }

    private val genre by lazy { findViewById<TextView>(R.id.genreValue) }

    private val country by lazy { findViewById<TextView>(R.id.countryValue) }

    private val likeButton by lazy { findViewById<ImageView>(R.id.like) }


    private var currentState = STATE_DEFAULT

    private val playButton by lazy { findViewById<ImageView>(R.id.playButton) }

    private val viewModel: PlayerViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val track = intent.getSerializableExtra("track") as Track

        if (savedInstanceState == null) {
            viewModel.setIsFavorite(track.isFavorite)
        }
        preparePlayer(track.previewUrl)

        val arrowBack = findViewById<ImageView>(R.id.arrowBack)

        arrowBack.setOnClickListener {
            finish()
        }

        playButton.setOnClickListener {
            playBackControl()

        }

        likeButton.setOnClickListener {
            viewModel.addOrRemoveTrackFromFavorite(track)
        }

        viewModel.isFavoriteLiveData.observe(this) { isFavorite ->
            likeButton.setImageResource(if (isFavorite) R.drawable.like_true else R.drawable.like_false)
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
                RoundedCorners(resources.getDimensionPixelSize(R.dimen.player_track_cover_corner_radius))
            )
            .placeholder(R.drawable.placeholder)
            .into(trackCover)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun preparePlayer(previewUrl: String) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            currentState = STATE_PREPARED

        }
        mediaPlayer.setOnCompletionListener {
            playButton.setImageResource(R.drawable.play_button)
            stopProgress()
            playTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(0)
            currentState = STATE_PREPARED
        }

    }

    private fun startPlayer() {
        mediaPlayer.start()
        currentState = STATE_PLAYING
        playButton.setImageResource(R.drawable.pause)
        startProgress()

    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        currentState = STATE_PAUSED
        playButton.setImageResource(R.drawable.play_button)
        stopProgress()
    }

    private fun playBackControl() {
        when (currentState) {
            STATE_PLAYING -> pausePlayer()
            STATE_PREPARED, STATE_PAUSED -> startPlayer()
        }
    }

    private fun startProgress() {
        job?.cancel()
        job = lifecycleScope.launch {
            while (isActive) {
                delay(DELAY)
                playTime.text = SimpleDateFormat(
                    "mm:ss",
                    Locale.getDefault()
                ).format(mediaPlayer.currentPosition)
            }
        }
    }

    private fun stopProgress() {
        job?.cancel()
    }

    companion object {
        const val STATE_DEFAULT = 0

        const val STATE_PREPARED = 1

        const val STATE_PLAYING = 2

        const val STATE_PAUSED = 3

        const val DELAY = 300L
    }


}