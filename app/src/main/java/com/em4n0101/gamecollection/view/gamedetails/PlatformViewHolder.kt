package com.em4n0101.gamecollection.view.gamedetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.em4n0101.gamecollection.model.Platform
import com.em4n0101.gamecollection.utils.getPlatformResourceFor
import kotlinx.android.synthetic.main.platform_item_view_holder.view.*

class PlatformViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(platform: Platform) = with(itemView) {
        platformItemImageView.setImageResource(getPlatformResourceFor(platform))
    }
}