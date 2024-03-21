package com.example.playlistmaker.editPlaylist.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.Constants
import com.example.playlistmaker.R
import com.example.playlistmaker.createPlaylist.presentation.PlaylistCreateFragment
import com.example.playlistmaker.playlist.presentation.PlaylistDetailsFragment
import com.example.playlistmaker.playlist.presentation.model.DetailedPlaylistModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPlaylistFragment : PlaylistCreateFragment() {

    override val viewModel: EditPlaylistViewModel by viewModel()
    lateinit var playlist : DetailedPlaylistModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createPlaylistButton.text = getString(R.string.save_text)
        binding.toolbarText.text = getString(R.string.edit_playlist_toolbar_text)
        playlist = BundleCompat.getParcelable(
            arguments ?: bundleOf(),
            PlaylistDetailsFragment.ARGS_KEY,
            DetailedPlaylistModel::class.java
        )!!
        binding.editText.setText(playlist.name)
        binding.descriptionEditText.setText(playlist.description)
        if (playlist.cover.isNotBlank()) binding.playlistCover.setPadding(0)
        Glide.with(this)
            .load(playlist.cover)
            .placeholder(R.drawable.add_photo)
            .error(R.drawable.add_photo)
            .transform(
                CenterCrop(),
                RoundedCorners(resources.getDimensionPixelSize(R.dimen.player_track_cover_corner_radius))
            )
            .into(binding.playlistCover)
    }

    override fun onMainButtonPressed() {
        var cover = Uri.EMPTY
        if (imageUri != Uri.EMPTY) {
            cover = viewModel.saveToInternal(imageUri, binding.editText.text.toString())
        }
        viewModel.saveUpdates(
            id = playlist.id,
            name = binding.editText.text.toString(),
            description = binding.descriptionEditText.text.toString(),
            cover = cover.toString(),
            trackList = playlist.trackList.joinToString(Constants.TRACK_LIST_SEPARATOR) { it.trackId }
        )
        findNavController().popBackStack()
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }
}