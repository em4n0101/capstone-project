package com.em4n0101.gamecollection.app.di

import com.em4n0101.gamecollection.model.repositories.GamesRepository
import com.em4n0101.gamecollection.networking.RemoteApi
import org.koin.dsl.module

val modelModule = module {
    single { GamesRepository(get()) }
}