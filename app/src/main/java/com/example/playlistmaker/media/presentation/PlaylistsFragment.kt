package com.example.playlistmaker.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentPlaylistsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistsFragment : Fragment() {

    private val viewModel by viewModel<PlaylistsViewModel>()
    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PlaylistAdapter {
            findNavController().navigate(
                R.id.playlistFragment,
                bundleOf(PLAYLIST_ID_KEY to it)
            )
        }
        binding.playlistList.adapter = adapter
        viewModel.playlistsLiveData.observe(viewLifecycleOwner) {
            binding.playlistList.isVisible = it.isNotEmpty()
            binding.noPlaylistsImageView.isVisible = it.isEmpty()
            binding.noPlaylistsTextView.isVisible = it.isEmpty()
            adapter.updatePlaylists(it)
        }
        binding.newPlaylistButton.setOnClickListener {
            findNavController().navigate(R.id.action_mediaFragment_to_playlistCreateFragment)
        }
    }


    companion object {
        const val PLAYLIST_ID_KEY = "playlistIdKey"

        @JvmStatic
        fun newInstance() = PlaylistsFragment()
    }

}