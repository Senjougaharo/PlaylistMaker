package com.example.playlistmaker.playlist.presentation

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.playlistmaker.Constants
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.PlaylistBinding
import com.example.playlistmaker.media.presentation.PlaylistsFragment
import com.example.playlistmaker.playlist.presentation.model.DetailedPlaylistModel
import com.example.playlistmaker.search.presentation.TrackAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : Fragment() {
    private var _binding: PlaylistBinding? = null
    private val binding get() = _binding!!
    private var currentPlaylist: DetailedPlaylistModel? = null
    private val viewModel by viewModel<PlaylistViewModel>()
    private var behavior: BottomSheetBehavior<LinearLayout>? = null
    private val screenHeight by lazy { Resources.getSystem().displayMetrics.heightPixels }
    private val layoutListener = OnGlobalLayoutListener {
        behavior?.peekHeight = screenHeight - binding.topView.height
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlaylistBinding.inflate(inflater, container, false)
        behavior = BottomSheetBehavior.from(binding.bottomView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playlistId = arguments?.getInt(PlaylistsFragment.PLAYLIST_ID_KEY)
        if (playlistId == null) Toast.makeText(
            requireContext(),
            getString(R.string.empty_playlist_id),
            Toast.LENGTH_LONG
        ).show() else viewModel.getPlaylistById(playlistId)
        val trackAdapter = TrackAdapter(
            onClick = {
                findNavController().navigate(
                    R.id.PlayerActivity,
                    bundleOf(Constants.ARG_KEY to it.trackId)
                )
            },
            onLongClick = { track ->
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage(R.string.delete_track_dialogue)
                    .setPositiveButton(R.string.yes_dialogue) { dialogue, _ ->
                        playlistId?.let { viewModel.deleteTrackFromPlaylist(track.trackId, it) }
                            ?: dialogue.dismiss()
                    }
                    .setNegativeButton(R.string.no_dialogue) { dialogue, _ -> dialogue.dismiss() }
                    .show()
            })
        binding.playlistList.adapter = trackAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playlistFlow?.collect {
                    trackAdapter.submitData(it.trackList)
                    currentPlaylist = it
                    if(currentPlaylist?.description.isNullOrBlank()) binding.playlistDescription.visibility = View.GONE
                    binding.playlistName.text = it.name
                    binding.playlistDescription.text = it.description
                    Glide.with(this@PlaylistFragment)
                        .load(it.cover)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(binding.playlistCover)
                    binding.tracksTimeMinutes.text = requireContext().resources.getQuantityString(
                        R.plurals.minutes_count,
                        it.totalTime,
                        it.totalTime
                    )
                    binding.playlistTracksCount.text = requireContext().resources.getQuantityString(
                        R.plurals.tracks_count,
                        it.count,
                        it.count
                    )
                }
            }
        }
        binding.returnButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.playlistMore.setOnClickListener {
            if(currentPlaylist != null) {
            PlaylistDetailsFragment
                .newInstance(currentPlaylist!!)
                .show(parentFragmentManager, null)
        }}
        binding.playlistShare.setOnClickListener {
            if (currentPlaylist?.trackList.isNullOrEmpty()) {
                showToast(R.string.playlist_is_empty)
            } else {
                val intent = viewModel.getShareIntent(
                    currentPlaylist?.trackList ?: emptyList(),
                    getString(R.string.text_pattern),
                    requireContext()
                )
                startActivity(intent)
            }
        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
    }

    override fun onPause() {
        super.onPause()
        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(@StringRes message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}