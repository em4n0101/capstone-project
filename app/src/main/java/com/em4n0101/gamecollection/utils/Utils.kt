package com.em4n0101.gamecollection.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.airbnb.lottie.LottieAnimationView
import com.em4n0101.gamecollection.R
import com.em4n0101.gamecollection.model.Platform
import com.squareup.picasso.Picasso

fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show()
}
fun String.removeHtmlTags() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)

fun setupImageForViewHolder(
    images: String?,
    intoImageView: ImageView,
    withLoaderView: LottieAnimationView,
    useOriginalImage: Boolean = false
) {
    if (images != null) {
        Picasso.get()
            .load(images)
            .error(R.drawable.no_image_available)
            .into(intoImageView)
    } else {
        withLoaderView.visibility = View.GONE
        intoImageView.setImageResource(R.drawable.no_image_available)
    }
}

fun getPlatformResourceFor(platform: Platform): Int {
    return when (platform.platform.id) {
        1 -> R.drawable.ic_windows
        2 -> R.drawable.ic_playstation
        3 -> R.drawable.ic_xbox
        4 -> R.drawable.ic_ios
        5 -> R.drawable.ic_apple
        7 -> R.drawable.icon_nintendo
        8 -> R.drawable.ic_android
        else -> R.drawable.ic_windows
    }
}