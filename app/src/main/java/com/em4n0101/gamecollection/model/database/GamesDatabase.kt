package com.em4n0101.gamecollection.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.em4n0101.gamecollection.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(GameConverters::class)
abstract class GameDatabase: RoomDatabase() {
    abstract val gameDatabaseDao: GameDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(
            context: Context
        ): GameDatabase {
            val dataBaseName = "games_database"

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    dataBaseName
                )
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}