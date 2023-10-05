package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.model.TrackSearchResponse
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivity() {
    
    lateinit var inputEditText: EditText
    var inputText: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        
        val searchHistory = (application as MyCustomApplication)
            .searchHistory
        
        val historyAdapter = TrackAdapter {}
        val trackAdapter = TrackAdapter {
            searchHistory.saveTrackToHistory(it)
            historyAdapter.submitData(searchHistory.readTrackHistory())
        }
        
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val noResultsStub = findViewById<LinearLayout>(R.id.search_lost)
        val connectionLost = findViewById<LinearLayout>(R.id.connection_lost)
        val trackHistoryRecycler = findViewById<RecyclerView>(R.id.track_history_recycler)
        val trackHistory = findViewById<ScrollView>(R.id.track_history)
        val clearHistoryButton = findViewById<MaterialButton>(R.id.clear_history_button)
        val tracksHistoryList = searchHistory.readTrackHistory()

        
        recyclerView.adapter = trackAdapter
        historyAdapter.submitData(tracksHistoryList)
        trackHistoryRecycler.adapter = historyAdapter
        if (tracksHistoryList.isEmpty()) {
            trackHistory.visibility = GONE
        } else {
            trackHistory.visibility = VISIBLE
        }
        clearHistoryButton.setOnClickListener {
            searchHistory.clearHistory()
            historyAdapter.submitData(emptyList())
            trackHistory.visibility = GONE
        }
        val callBack = object : Callback<TrackSearchResponse> {
            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                val body = response.body()
                if (body == null || body.results.isEmpty()) {
                    recyclerView.visibility = GONE
                    noResultsStub.visibility = VISIBLE
                    
                } else {
                    recyclerView.visibility = VISIBLE
                    noResultsStub.visibility = GONE
                    trackAdapter.submitData(response.body()!!.results)
                }
                connectionLost.visibility = GONE
            }
            
            
            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
                connectionLost.visibility = VISIBLE
                recyclerView.visibility = GONE
                noResultsStub.visibility = GONE
                
            }
        }
        
        inputEditText = findViewById(R.id.inputEditText)
        inputEditText.requestFocus()
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                retrofit.getTrack(inputText).enqueue(callBack)
                true
            } else
                false
        }
        
        val refresh = findViewById<MaterialButton>(R.id.refresh)
        
        refresh.setOnClickListener {
            
            retrofit.getTrack(inputText).enqueue(callBack)
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
                
                inputText = s.toString()
                
                noResultsStub.visibility = GONE
                
                if (inputEditText.hasFocus() && inputEditText.text.isEmpty()) {
                    if (searchHistory.readTrackHistory().isEmpty()) {
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
    }
    
    private fun hideSoftKeyboard(view: View) {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    
}













