package com.em4n0101.gamecollection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Game (
    val id: Int?,
    val slug: String,
    val name: String,
    val released: String,
    val background_image: String,
    val rating: Float?,
    val rating_top: Float?,
    val metacritic: Float?,
    val playtime: Float?,
    val parent_platforms: List<Platform>?,
    val genres: List<Genre>?,
    val stores: List<Store>?,
    val clip: Clip?,
    val short_screenshots: List<ScreenShot>?
) : Parcelable