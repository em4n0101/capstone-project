package com.em4n0101.gamecollection.app

import android.app.Application
import android.content.Context
import com.em4n0101.gamecollection.app.di.modelModule
import com.em4n0101.gamecollection.app.di.networkModule
import com.em4n0101.gamecollection.app.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GamesApplication: Application() {
    companion object {
        private lateinit var instance: GamesApplication
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger()
            androidContext(this@GamesApplication)
            modules(listOf(networkModule, viewModelsModule, modelModule))
        }
    }
}