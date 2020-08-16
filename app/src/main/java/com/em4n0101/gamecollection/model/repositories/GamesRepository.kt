package com.em4n0101.gamecollection.model.repositories

import com.em4n0101.gamecollection.model.Game
import com.em4n0101.gamecollection.model.database.GameDatabase
import com.em4n0101.gamecollection.model.database.GameDatabaseDao
import com.em4n0101.gamecollection.networking.RemoteApi
import kotlinx.coroutines.flow.Flow

class GamesRepository (private val remoteApi: RemoteApi, private val databaseDao: GameDatabaseDao) {
    /**
     * Network operations
     */
    suspend fun getTopGames() = remoteApi.getTopGames()

    suspend fun getGameDetails(gameId: Int) = remoteApi.getGameDetails(gameId)

    suspend fun getSearchForAGame(inputToSearch: String)
            = remoteApi.searchForAGame(inputToSearch)

    suspend fun getGamesForGenre(genreName: String) = remoteApi.searchGamesForGenre(genreName)

    /**
     * Database operations
     */
    suspend fun insertGame(game: Game) = databaseDao.insertGame(game)

    fun getFavoriteGames(): Flow<List<Game>> = databaseDao.getAllGames()

    fun getFavoriteGameBy(id: Int): Flow<Game?> = databaseDao.getGameBy(id)

    suspend fun deleteFavoriteGameBy(id: Int) = databaseDao.deleteGameBy(id)

}