package com.example.playlistmaker.search.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.player.domain.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackAdapter(
    private val onClick: (Track) -> Unit,
    private val onLongClick: (Track) -> Unit = {}
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    inner class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun getSimpleDataFormat(track: Track): String {
            return SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        }

        fun bind(track: Track) {
            itemView.findViewById<TextView>(R.id.track_name).text = track.trackName
            itemView.findViewById<TextView>(R.id.artist_name).text = track.artistName
            itemView.findViewById<TextView>(R.id.duration).text = getSimpleDataFormat(track)

            val cover = itemView.findViewById<ImageView>(R.id.cover)
            Glide.with(cover)
                .load(track.artworkUrl100)
                .transform(
                    CenterInside(),
                    RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.track_cover_corner_radius))
                )
                .placeholder(R.drawable.placeholder)
                .into(cover)

            itemView.setOnClickListener {
                onClick.invoke(track)
            }
            itemView.setOnLongClickListener {
                onLongClick(track)
                true
            }

        }


    }

    private var data: List<Track> = arrayListOf()

    fun submitData(data: List<Track>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val viewHolder = TrackViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.track_list_item, parent, false)
        )

        return viewHolder
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = data[position]

        holder.bind(track)

    }

}