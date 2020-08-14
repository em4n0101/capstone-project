package com.em4n0101.gamecollection.model.repositories

import com.em4n0101.gamecollection.networking.RemoteApi

class GamesRepository (private val remoteApi: RemoteApi) {
    /**
     * Game operations
     */
    suspend fun getTopGames() = remoteApi.getTopGames()
}