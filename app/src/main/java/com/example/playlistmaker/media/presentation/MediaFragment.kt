package com.example.playlistmaker.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentMediaBinding
import com.google.android.material.tabs.TabLayoutMediator

class MediaFragment : Fragment() {

    private var _binding: FragmentMediaBinding? = null

    private val binding get() = _binding!!

    private lateinit var tabMediator: TabLayoutMediator
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MediaAdapter(this)

        binding.viewPager.adapter = adapter

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->

            if (position == 0){
                tab.text = getString(R.string.favorite_tracks)

            }
            else tab.text = getString(R.string.playlists)
        }
        tabMediator.attach()

        binding.arrowBack.setOnClickListener{

            onBackPressedDispatcher.onBackPressed() // TODO: fix
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}