package com.em4n0101.gamecollection.model.database

import androidx.room.Entity
import com.em4n0101.gamecollection.model.*

class GameModel (
    val id: Int?,
    val slug: String,
    val name: String,
    val released: String?,
    val background_image: String,
    val rating: Float?,
    val rating_top: Float?,
    val metacritic: Float?,
    val playtime: Float?,
    val parent_platforms: List<Platform>?,
    val genres: List<Genre>?,
    val clip: Clip?,
    val short_screenshots: List<ScreenShot>?
)