package com.em4n0101.gamecollection.networking

import com.em4n0101.gamecollection.model.response.TopGamesResponse
import retrofit2.http.GET

interface RemoteApiService {
    @GET("games?ordering=-added")
    suspend fun getTopGames(): TopGamesResponse
}