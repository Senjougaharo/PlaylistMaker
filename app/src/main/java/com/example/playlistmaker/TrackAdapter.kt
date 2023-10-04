package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackAdapter( val data: ArrayList<Track>): RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    class TrackViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(track: Track){
            itemView.findViewById<TextView>(R.id.track_name).text = track.trackName
            itemView.findViewById<TextView>(R.id.artist_name).text = track.artistName
            itemView.findViewById<TextView>(R.id.duration).text = track.trackTime
            val cover = itemView.findViewById<ImageView>(R.id.cover)
            Glide.with(cover)
                .load(track.artworkUrl100)
                .transform(
                    CenterInside(),
                    RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.track_cover_corner_radius))
                )
                .placeholder(R.drawable.placeholder)
                .into(cover)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val viewHolder = TrackViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_list_item,parent,false))

    return viewHolder
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = data[position]

        holder.bind(track)

    }

}