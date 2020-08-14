package com.em4n0101.gamecollection.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.em4n0101.gamecollection.model.repositories.GamesRepository
import com.em4n0101.gamecollection.model.response.Failure
import com.em4n0101.gamecollection.model.response.Game
import com.em4n0101.gamecollection.model.response.Success
import kotlinx.coroutines.launch

class TopGamesViewModel(private val repository: GamesRepository): ViewModel() {
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _topGamesLiveData = MutableLiveData<List<Game>>()
    val topGamesLiveData: LiveData<List<Game>> get() = _topGamesLiveData

    private val _errorLiveData = MutableLiveData<Throwable?>()
    val errorLiveData: LiveData<Throwable?> get() = _errorLiveData

    fun getTopGames() {
        _loadingLiveData.postValue(true)

        viewModelScope.launch {
            val getTopGamesResponse = repository.getTopGames()
            _loadingLiveData.postValue(false)

            if (getTopGamesResponse is Success) {
                _topGamesLiveData.postValue(getTopGamesResponse.data.results)
            } else {
                val failure = getTopGamesResponse as Failure
                _errorLiveData.postValue(failure.error)
            }
        }
    }
}