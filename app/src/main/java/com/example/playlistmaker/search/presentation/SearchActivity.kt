package com.example.playlistmaker.search.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.MyCustomApplication
import com.example.playlistmaker.R
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.player.presentation.PlayerActivity
import com.example.playlistmaker.search.domain.SearchState
import com.google.android.material.button.MaterialButton


class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val noResultsStub by lazy { findViewById<LinearLayout>(R.id.search_lost) }
    private val connectionLost by lazy { findViewById<LinearLayout>(R.id.connection_lost) }
    private val trackHistoryRecycler by lazy { findViewById<RecyclerView>(R.id.track_history_recycler) }
    private val trackHistory by lazy { findViewById<ScrollView>(R.id.track_history) }
    private val clearHistoryButton by lazy { findViewById<MaterialButton>(R.id.clear_history_button) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }
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

        progressBar.visibility = VISIBLE
        recyclerView.visibility = GONE

        searchTrack()


    }

    lateinit var inputEditText: EditText
    var inputText: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val viewModelFactory = (application as MyCustomApplication).viewModelFactory
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        viewModel.liveData.observe(this) { state ->
            when (state){
                is SearchState.Success -> {
                    if (state.trackList.isNotEmpty()) {
                        recyclerView.visibility = VISIBLE
                        noResultsStub.visibility = GONE
                        trackAdapter.submitData(state.trackList)
                    } else {
                        recyclerView.visibility = GONE
                        noResultsStub.visibility = VISIBLE
                    }
                    connectionLost.visibility = GONE
                    progressBar.visibility = GONE
                }
                is SearchState.Error -> {
                    connectionLost.visibility = VISIBLE
                    recyclerView.visibility = GONE
                    noResultsStub.visibility = GONE
                    connectionLost.visibility = GONE
                    progressBar.visibility = GONE
                }
            }
        }


        val tracksHistoryList = viewModel.readTrackHistory()

        recyclerView.adapter = trackAdapter
        historyAdapter.submitData(tracksHistoryList)
        trackHistoryRecycler.adapter = historyAdapter
        if (tracksHistoryList.isEmpty()) {
            trackHistory.visibility = GONE
        } else {
            trackHistory.visibility = VISIBLE
        }
        clearHistoryButton.setOnClickListener {
            viewModel.clearHistory()
            historyAdapter.submitData(emptyList())
            trackHistory.visibility = GONE
        }


        inputEditText = findViewById(R.id.inputEditText)
        inputEditText.requestFocus()


        val refresh = findViewById<MaterialButton>(R.id.refresh)

        refresh.setOnClickListener {

            searchTrack()
        }


        val arrowBack = findViewById<ImageView>(R.id.arrowBack)

        arrowBack.setOnClickListener {
            finish()
        }

        val clearButton = findViewById<ImageView>(R.id.clearIcon)

        clearButton.setOnClickListener {
            inputEditText.setText("")
            hideSoftKeyboard(it)
            recyclerView.visibility = GONE
            noResultsStub.visibility = GONE
            connectionLost.visibility = GONE

        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                clearButton.visibility = clearButtonVisibility(s)

                searchDebounce()

                inputText = s.toString()

                noResultsStub.visibility = GONE

                if (inputEditText.hasFocus() && inputEditText.text.isEmpty()) {
                    if (viewModel.readTrackHistory().isEmpty()) {
                        trackHistory.visibility = GONE
                    } else {
                        trackHistory.visibility = VISIBLE
                    }
                    recyclerView.visibility = GONE
                    connectionLost.visibility = GONE
                    noResultsStub.visibility = GONE
                } else {
                    trackHistory.visibility = GONE
                    recyclerView.visibility = VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {


            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

    }


    fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            GONE
        } else {
            VISIBLE
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(BUBA, inputEditText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        inputText = savedInstanceState.getString(BUBA, "")
        inputEditText.setText(inputText)

    }


    companion object {
        const val BUBA = "BUBA"

        const val SEARCH_DELAY = 2000L

        const val CLICK_DELAY = 1000L
    }

    private fun openPlayer(track: Track) {
        if (clickDebounce()) {
            val intent = Intent(this, PlayerActivity::class.java).putExtra("track", track)
            startActivity(intent)
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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













