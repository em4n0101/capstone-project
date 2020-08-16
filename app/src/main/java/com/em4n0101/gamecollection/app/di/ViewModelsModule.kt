package com.em4n0101.gamecollection.app.di

import com.em4n0101.gamecollection.viewmodel.InnerSearchGamesByGenreViewModel
import com.em4n0101.gamecollection.viewmodel.gamedetails.GameDetailsViewModel
import com.em4n0101.gamecollection.viewmodel.profile.ProfileViewModel
import com.em4n0101.gamecollection.viewmodel.search.SearchGameViewModel
import com.em4n0101.gamecollection.viewmodel.topgames.TopGamesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { TopGamesViewModel(get()) }
    viewModel { GameDetailsViewModel(get()) }
    viewModel { SearchGameViewModel(get()) }
    viewModel { InnerSearchGamesByGenreViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}