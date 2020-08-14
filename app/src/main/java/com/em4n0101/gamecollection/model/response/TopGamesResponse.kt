package com.em4n0101.gamecollection.model.response

import kotlinx.serialization.Serializable

@Serializable
data class TopGamesResponse (
    val results: List<Game>
)