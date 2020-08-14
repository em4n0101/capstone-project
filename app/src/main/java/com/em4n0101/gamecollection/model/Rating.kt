package com.em4n0101.gamecollection.model

import kotlinx.serialization.Serializable

@Serializable
data class Rating (
    val id: Int,
    val name: String
)