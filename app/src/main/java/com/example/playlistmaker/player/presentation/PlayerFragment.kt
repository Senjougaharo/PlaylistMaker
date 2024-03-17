package com.example.playlistmaker.player.presentation

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.addToPlaylist.presentation.AddToPlaylistFragment
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.player.domain.model.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerFragment : Fragment() {

    private val mediaPlayer = MediaPlayer()

    private var job: Job? = null

    private var _binding: ActivityPlayerBinding? = null
    private val binding get() = _binding!!

    private var currentState = STATE_DEFAULT

    private val viewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val track =
            BundleCompat.getParcelable(arguments ?: bundleOf(), "track", Track::class.java)!!
        preparePlayer(track.previewUrl)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val track =
            BundleCompat.getParcelable(arguments ?: bundleOf(), "track", Track::class.java)!!
        viewModel.checkIsFavorite(track.trackId)
//        if (savedInstanceState == null)
//            preparePlayer(track.previewUrl)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.playButton.setOnClickListener {
            playBackControl()

        }

        binding.like.setOnClickListener {
            viewModel.addOrRemoveTrackFromFavorite(track)
        }

        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner) { isFavorite ->
            binding.like.setImageResource(if (isFavorite) R.drawable.like_true else R.drawable.like_false)
        }

        binding.addToPlaylist.setOnClickListener {
            AddToPlaylistFragment
                .newInstance(track)
                .show(parentFragmentManager, null)
        }

        binding.trackName.text = track.trackName

        binding.trackArtist.text = track.artistName

        binding.duration.text = track.timeFormat()

        binding.album.text = track.collectionName

        binding.year.text = track.releaseDate.take(4)

        binding.genre.text = track.primaryGenreName

        binding.country.text = track.country

        Glide.with(this)
            .load(track.getCoverArtwork())
            .transform(
                CenterInside(),
                RoundedCorners(resources.getDimensionPixelSize(R.dimen.player_track_cover_corner_radius))
            )
            .placeholder(R.drawable.placeholder)
            .into(binding.trackCover)
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
        mediaPlayer.release()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun preparePlayer(previewUrl: String) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            currentState = STATE_PREPARED

        }
        mediaPlayer.setOnCompletionListener {
            binding.playButton.setImageResource(R.drawable.play_button)
            stopProgress()
            binding.playTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(0)
            currentState = STATE_PREPARED
        }

    }

    private fun startPlayer() {
        mediaPlayer.start()
        currentState = STATE_PLAYING
        binding.playButton.setImageResource(R.drawable.pause)
        startProgress()

    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        currentState = STATE_PAUSED
        binding.playButton.setImageResource(R.drawable.play_button)
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
                binding.playTime.text = SimpleDateFormat(
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