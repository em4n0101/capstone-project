package com.em4n0101.gamecollection.view.gamedetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.em4n0101.gamecollection.model.ScreenShot
import com.em4n0101.gamecollection.utils.setupImageForViewHolder
import kotlinx.android.synthetic.main.screenshot_item_view_holder.view.*

class ScreenshotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(screenshot: ScreenShot, onScreenshotClick: (ScreenShot) -> Unit) = with(itemView) {
        setOnClickListener { onScreenshotClick(screenshot) }
        setupImageForViewHolder(screenshot.image, screenshotItemImageView, loaderAnimationView)
    }
}