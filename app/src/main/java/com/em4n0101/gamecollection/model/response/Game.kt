package com.em4n0101.gamecollection.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val slug: String,
    val name: String,
    val released: String,
    val background_image: String,
    val rating: Float?,
    val rating_top: Float?
)