package com.example.playlistmaker.search.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.search.domain.SearchState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    private var isClickAllowed = true
    private val mainHandler = Handler(Looper.getMainLooper())
    private var lastSearchedText = ""
    private var searchJob: Job? = null

    private val trackAdapter = TrackAdapter(
        onClick = {
            viewModel.saveTrackToHistory(it)
            historyAdapter.submitData(viewModel.readTrackHistory())

            openPlayer(it)
        }
    )

    private val historyAdapter = TrackAdapter (
        onClick = {
            openPlayer(it)
        }
    )

    var inputText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is SearchState.Success -> {
                    if (state.trackList.isNotEmpty()) {
                        binding.recyclerView.isVisible = binding.inputEditText.text.isNotEmpty()
                        binding.searchLost.root.visibility = View.GONE
                        trackAdapter.submitData(state.trackList)
                    } else {
                        binding.recyclerView.visibility = View.GONE
                        binding.searchLost.root.visibility = View.VISIBLE
                    }
                    binding.connectionLost.root.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }

                is SearchState.Error -> {
                    binding.connectionLost.root.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.searchLost.root.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }


        val tracksHistoryList = viewModel.readTrackHistory()

        binding.recyclerView.adapter = trackAdapter
        historyAdapter.submitData(tracksHistoryList)
        binding.trackHistory.trackHistoryRecycler.adapter = historyAdapter
        if (tracksHistoryList.isEmpty()) {
            binding.trackHistory.root.visibility = View.GONE
        } else {
            binding.trackHistory.root.visibility = View.VISIBLE
        }
        binding.trackHistory.clearHistoryButton.setOnClickListener {
            viewModel.clearHistory()
            historyAdapter.submitData(emptyList())
            binding.trackHistory.root.visibility = View.GONE
        }






        binding.connectionLost.refresh.setOnClickListener {

            searchTrack(lastSearchedText)
        }



        binding.clearIcon.setOnClickListener {
            binding.inputEditText.setText("")
            hideSoftKeyboard(it)
            binding.recyclerView.visibility = View.GONE
            binding.searchLost.root.visibility = View.GONE
            binding.connectionLost.root.visibility = View.GONE

        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                binding.clearIcon.visibility = clearButtonVisibility(s)

                searchDebounce(s.toString())

                inputText = s.toString()

                binding.searchLost.root.visibility = View.GONE

                if (binding.inputEditText.hasFocus() && binding.inputEditText.text.isEmpty()) {
                    if (viewModel.readTrackHistory().isEmpty()) {
                        binding.trackHistory.root.visibility = View.GONE
                    } else {
                        binding.trackHistory.root.visibility = View.VISIBLE
                    }
                    binding.recyclerView.visibility = View.GONE
                    binding.connectionLost.root.visibility = View.GONE
                    binding.searchLost.root.visibility = View.GONE
                } else {
                    binding.trackHistory.root.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {


            }
        }
        binding.inputEditText.addTextChangedListener(simpleTextWatcher)
        binding.inputEditText.requestFocus()

    }

    fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(BUBA, binding.inputEditText.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        inputText = savedInstanceState?.getString(BUBA, "") ?: ""
        binding.inputEditText.setText(inputText)
    }

    companion object {
        const val BUBA = "BUBA"

        const val SEARCH_DELAY = 2000L

        const val CLICK_DELAY = 1000L
    }

    private fun openPlayer(track: Track) {
        if (clickDebounce()) {
            findNavController().navigate(R.id.PlayerActivity, bundleOf("track" to track))
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val manager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun searchDebounce(text: String?) {
        if (text == null || text == "") return
        if (text == lastSearchedText) return
        lastSearchedText = text
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            delay(SEARCH_DELAY)
            searchTrack(text)
        }
    }

    private fun searchTrack(text: String) {
        viewModel.searchTracks(text)
    }

    private fun clickDebounce(): Boolean {

        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            mainHandler.postDelayed({ isClickAllowed = true }, CLICK_DELAY)


        }

        return current
    }



}