package com.em4n0101.gamecollection.model.repositories

import com.em4n0101.gamecollection.networking.RemoteApi

class GamesRepository (private val remoteApi: RemoteApi) {
    /**
     * Game operations
     */
    suspend fun getTopGames() = remoteApi.getTopGames()

    suspend fun getGameDetails(gameId: Int) = remoteApi.getGameDetails(gameId)

    suspend fun getSearchForAGame(inputToSearch: String)
            = remoteApi.searchForAGame(inputToSearch)

    suspend fun getGamesForGenre(genreName: String) = remoteApi.searchGamesForGenre(genreName)
}