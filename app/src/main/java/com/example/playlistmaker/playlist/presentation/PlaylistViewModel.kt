package com.example.playlistmaker.playlist.presentation

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.R
import com.example.playlistmaker.createPlaylist.domain.PlaylistInteractor
import com.example.playlistmaker.player.domain.model.Track
import com.example.playlistmaker.playlist.presentation.model.DetailedPlaylistModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel(private val interactor: PlaylistInteractor): ViewModel() {

    var playlistFlow: Flow<DetailedPlaylistModel>? = null

    private val _removingState = MutableStateFlow(false)
    val removingState = _removingState.asStateFlow()

    fun getPlaylistById(id: Int) {
        playlistFlow = interactor.getPlaylistById(id)
    }
    fun deleteTrackFromPlaylist(trackId: String, playlistId: Int) {
        viewModelScope.launch {
            interactor.deleteTrackFromPlaylist(trackId, playlistId)
        }
    }
    fun deletePlaylist(playlist: DetailedPlaylistModel) {
        viewModelScope.launch {
            val removingResult = interactor.deletePlaylist(playlist)
            _removingState.value = removingResult
        }
    }
    fun getShareIntent(trackList: List<Track>, type: String, context: Context): Intent {
        val text =  buildString {
            append(context.resources.getQuantityString(R.plurals.tracks_count, trackList.size, trackList.size))
            append("\n")
            trackList.forEachIndexed { index, track ->
                append("${index + 1}. ${track.artistName} - ${track.trackName} (${track.timeFormat()})")
                append("\n")
            }
        }
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            this.type = type
        }

        return Intent.createChooser(sendIntent, null)
    }
}