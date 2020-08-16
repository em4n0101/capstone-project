package com.em4n0101.gamecollection.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.em4n0101.gamecollection.model.Game
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface GameDatabaseDao {
    /**
     * Operations for games
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM games_table WHERE id = :id")
    fun getGameBy(id: Int): Flow<Game?>

    @ExperimentalCoroutinesApi
    fun getGameByIdDistinctUntilChanged(id: Int) =
        getGameBy(id).distinctUntilChanged()

    @Query("SELECT * FROM games_table")
    fun getAllGames(): Flow<List<Game>>

    @Query("DELETE FROM games_table WHERE id = :id")
    suspend fun deleteGameBy(id: Int)
}