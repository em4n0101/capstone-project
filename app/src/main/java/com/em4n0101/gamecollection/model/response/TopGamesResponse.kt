package com.em4n0101.gamecollection.model.response

import com.em4n0101.gamecollection.model.Game
import kotlinx.serialization.Serializable

@Serializable
data class TopGamesResponse (
    val results: List<Game>
)