package com.em4n0101.gamecollection.model.response

import com.em4n0101.gamecollection.model.Rating
import kotlinx.serialization.Serializable

@Serializable
class GameDetailsResponse (
    val slug: String,
    val description: String,
    val esrb_rating: Rating?
)