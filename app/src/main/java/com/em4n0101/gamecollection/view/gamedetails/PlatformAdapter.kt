package com.em4n0101.gamecollection.view.gamedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.Platform

class PlatformAdapter : RecyclerView.Adapter<PlatformViewHolder>() {
    private val platformList: MutableList<Platform> = mutableListOf()

    fun setData(platforms: List<Platform>) {
        platformList.clear()
        platformList.addAll(platforms)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.platform_item_view_holder, parent, false)
        return PlatformViewHolder(
            itemView
        )
    }

    override fun getItemCount() = platformList.size

    override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
        holder.bind(platformList[position])
    }
}