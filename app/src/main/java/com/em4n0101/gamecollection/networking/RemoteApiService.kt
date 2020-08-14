package com.em4n0101.gamecollection.networking

import com.em4n0101.gamecollection.model.response.GameDetailsResponse
import com.em4n0101.gamecollection.model.response.TopGamesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApiService {
    @GET("games?ordering=-added")
    suspend fun getTopGames(): TopGamesResponse

    @GET("games/{gameId}")
    suspend fun getGameDetails(@Path("gameId") gameId: String): GameDetailsResponse
}