package com.em4n0101.gamecollection.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.em4n0101.gamecollection.model.repositories.GamesRepository

class ProfileViewModel(private val repository: GamesRepository): ViewModel() {
    fun getFavoriteGames() = repository.getFavoriteGames().asLiveData()
}