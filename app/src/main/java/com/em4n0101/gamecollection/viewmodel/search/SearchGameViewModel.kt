package com.em4n0101.gamecollection.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.em4n0101.gamecollection.model.Failure
import com.em4n0101.gamecollection.model.Game
import com.em4n0101.gamecollection.model.Success
import com.em4n0101.gamecollection.model.repositories.GamesRepository
import kotlinx.coroutines.launch

class SearchGameViewModel(private val repository: GamesRepository): ViewModel() {
    private val _gamesFoundedLiveData = MutableLiveData<List<Game>>()
    val gamesFoundedLiveData: LiveData<List<Game>> get() = _gamesFoundedLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<Throwable?>()
    val errorLiveData: LiveData<Throwable?> get() = _errorLiveData

    fun searchForAGame(inputToSearch: String) {
        _loadingLiveData.postValue(true)

        viewModelScope.launch {
            val getSearchGamesResponse = repository.getSearchForAGame(inputToSearch)
            _loadingLiveData.postValue(false)

            if (getSearchGamesResponse is Success) {
                _gamesFoundedLiveData.postValue(getSearchGamesResponse.data.results)
            } else {
                val failure = getSearchGamesResponse as Failure
                println("Error get shows: $failure")
                println("Error get shows: ${failure.error}")
                _errorLiveData.postValue(failure.error)
            }
        }
    }
}