package com.em4n0101.gamecollection.app.di

import com.em4n0101.gamecollection.viewmodel.TopGamesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { TopGamesViewModel(get()) }
}