package com.em4n0101.gamecollection.view.topgames

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.Game

class TopGamesAdapter (private val onGameClicked: (Game) -> Unit): RecyclerView.Adapter<GameViewHolder>() {

    private val gameList: MutableList<Game> = mutableListOf()

    fun setData(games: List<Game>) {
        gameList.clear()
        gameList.addAll(games)
        notifyDataSetChanged()
        println("SetData: ${games.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_item_view_holder, parent, false)
        return GameViewHolder(itemView)
    }

    override fun getItemCount() = 20

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(gameList[position], onGameClicked)
    }
}