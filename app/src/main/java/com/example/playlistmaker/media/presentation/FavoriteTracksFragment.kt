package com.example.playlistmaker.media.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentFavoriteTracksBinding
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.player.presentation.PlayerActivity
import com.example.playlistmaker.search.presentation.SearchFragment
import com.example.playlistmaker.search.presentation.TrackAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteTracksFragment : Fragment() {

    private var isClickAllowed = true
    private val mainHandler = Handler(Looper.getMainLooper())

    private var _binding : FragmentFavoriteTracksBinding?=null

    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoriteTracksViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteTracksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TrackAdapter{

            openPlayer(it)
        }
        binding.recyclerView.adapter = adapter
        viewModel.liveData.observe(viewLifecycleOwner){
            when (it){
                is FavoriteScreenState.Empty -> {
                    binding.emptyFav.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is FavoriteScreenState.NotEmpty -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.emptyFav.visibility = View.GONE
                    adapter.submitData(it.list)
                }
            }
        }
    }
    private fun openPlayer(track: Track) {
        if (clickDebounce()) {
            val intent =
                Intent(requireContext(), PlayerActivity::class.java).putExtra("track", track)
            startActivity(intent)
        }
    }


    private fun clickDebounce(): Boolean {

        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            mainHandler.postDelayed({ isClickAllowed = true }, SearchFragment.CLICK_DELAY)


        }

        return current
    }

    companion object{
        fun newInstance(): FavoriteTracksFragment {
            return FavoriteTracksFragment()
        }


    }
}

