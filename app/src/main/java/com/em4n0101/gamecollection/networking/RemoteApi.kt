package com.em4n0101.gamecollection.networking

import com.em4n0101.gamecollection.model.response.Failure
import com.em4n0101.gamecollection.model.response.Result
import com.em4n0101.gamecollection.model.response.Success
import com.em4n0101.gamecollection.model.response.TopGamesResponse

class RemoteApi(private val remoteApiService: RemoteApiService) {
    /**
     * Get top games, ordering by released date
     */
    suspend fun getTopGames(): Result<TopGamesResponse> = try {
        val data = remoteApiService.getTopGames()
        Success(data)
    } catch (error: Throwable) {
        Failure(error)
    }
}