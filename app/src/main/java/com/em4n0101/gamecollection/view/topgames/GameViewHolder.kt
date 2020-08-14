package com.em4n0101.gamecollection.view.topgames

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.em4n0101.gamecollection.model.Game
import com.em4n0101.gamecollection.utils.setupImageForViewHolder
import kotlinx.android.synthetic.main.game_item_view_holder.view.*

class GameViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(game: Game, onShowClick: (Game) -> Unit) = with(itemView) {
        itemBigTitle.text = game.name
        setOnClickListener { onShowClick(game) }
        setupImageForViewHolder(game.background_image, itemBigPoster, loaderBigAnimationView)
    }
}