package com.em4n0101.gamecollection.view.gamedetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.em4n0101.gamecollection.model.Genre
import kotlinx.android.synthetic.main.genre_item_view_holder.view.*

class GenreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(genre: Genre) = with(itemView) {
        genreButton.text = genre.name
    }
}