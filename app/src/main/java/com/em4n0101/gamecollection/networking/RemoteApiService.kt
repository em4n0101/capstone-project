package com.em4n0101.gamecollection.networking

import com.em4n0101.gamecollection.model.response.GameDetailsResponse
import com.em4n0101.gamecollection.model.response.TopGamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApiService {
    @GET("games?ordering=-added")
    suspend fun getTopGames(): TopGamesResponse

    @GET("games/{gameId}")
    suspend fun getGameDetails(@Path("gameId") gameId: String): GameDetailsResponse

    @GET("games?ordering=-added")
    suspend fun searchGamesForUserInput(@Query("search") search: String): TopGamesResponse

    @GET("games?ordering=-added")
    suspend fun searchGamesForGenre(@Query("genres") genres: String): TopGamesResponse
}