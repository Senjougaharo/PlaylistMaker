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
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.player.presentation.PlayerActivity
import com.example.playlistmaker.search.domain.SearchState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    private var isClickAllowed = true
    private val mainHandler = Handler(Looper.getMainLooper())

    private val trackAdapter = TrackAdapter {
        viewModel.saveTrackToHistory(it)
        historyAdapter.submitData(viewModel.readTrackHistory())

        openPlayer(it)
    }

    private val historyAdapter = TrackAdapter {
        openPlayer(it)
    }

    private val searchRunnable = Runnable {

        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE

        searchTrack()


    }

    lateinit var inputEditText: EditText
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
                is SearchState.Success -> {
                    if (state.trackList.isNotEmpty()) {
                        binding.recyclerView.visibility = View.VISIBLE
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



        binding.inputEditText.requestFocus()



        binding.connectionLost.refresh.setOnClickListener {

            searchTrack()
        }



        binding.clearIcon.setOnClickListener {
            inputEditText.setText("")
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

                searchDebounce()

                inputText = s.toString()

                binding.searchLost.root.visibility = View.GONE

                if (inputEditText.hasFocus() && inputEditText.text.isEmpty()) {
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
        inputEditText.addTextChangedListener(simpleTextWatcher)

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
        outState.putString(BUBA, inputEditText.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        inputText = savedInstanceState?.getString(BUBA, "") ?: ""
        inputEditText.setText(inputText)
    }

    companion object {
        const val BUBA = "BUBA"

        const val SEARCH_DELAY = 2000L

        const val CLICK_DELAY = 1000L
    }

    private fun openPlayer(track: Track) {
        if (clickDebounce()) {
            val intent =
                Intent(requireContext(), PlayerActivity::class.java).putExtra("track", track)
            startActivity(intent)
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val manager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun searchDebounce() {
        mainHandler.removeCallbacks(searchRunnable)
        mainHandler.postDelayed(searchRunnable, SEARCH_DELAY)
    }

    private fun searchTrack() {
        viewModel.searchTracks(inputText)
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