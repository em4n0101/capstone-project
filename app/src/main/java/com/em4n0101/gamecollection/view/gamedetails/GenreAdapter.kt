package com.em4n0101.gamecollection.view.gamedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.Genre

class GenreAdapter : RecyclerView.Adapter<GenreViewHolder>() {
    private val genreList: MutableList<Genre> = mutableListOf()

    fun setData(genres: List<Genre>) {
        genreList.clear()
        genreList.addAll(genres)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.genre_item_view_holder, parent, false)
        return GenreViewHolder(itemView)
    }

    override fun getItemCount() = genreList.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genreList[position])
    }
}