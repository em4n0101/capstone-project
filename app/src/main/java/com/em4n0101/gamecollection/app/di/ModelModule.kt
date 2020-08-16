package com.em4n0101.gamecollection.app.di

import com.em4n0101.gamecollection.model.database.GameDatabase
import com.em4n0101.gamecollection.model.repositories.GamesRepository
import com.em4n0101.gamecollection.networking.RemoteApi
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val modelModule = module {
    single { GameDatabase.getDatabase(androidApplication()) }

    single { get<GameDatabase>().gameDatabaseDao }

    single { GamesRepository(get(), get()) }
}