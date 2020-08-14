package com.em4n0101.gamecollection.view.gamedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.ScreenShot

class ScreenshotAdapter(private val onScreenshotClicked: (ScreenShot) -> Unit): RecyclerView.Adapter<ScreenshotViewHolder>() {

    private val screenshotList: MutableList<ScreenShot> = mutableListOf()

    fun setData(screenshots: List<ScreenShot>) {
        screenshotList.clear()
        screenshotList.addAll(screenshots)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.screenshot_item_view_holder, parent, false)
        return ScreenshotViewHolder(itemView)
    }

    override fun getItemCount() = screenshotList.size

    override fun onBindViewHolder(holder: ScreenshotViewHolder, position: Int) {
        holder.bind(screenshotList[position], onScreenshotClicked)
    }
}