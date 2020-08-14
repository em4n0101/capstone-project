package com.em4n0101.gamecollection.viewmodel.gamedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.em4n0101.gamecollection.model.Failure
import com.em4n0101.gamecollection.model.Success
import com.em4n0101.gamecollection.model.repositories.GamesRepository
import com.em4n0101.gamecollection.model.response.GameDetailsResponse
import kotlinx.coroutines.launch

class GameDetailsViewModel(private val repository: GamesRepository): ViewModel() {
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _gameDetailsLiveData = MutableLiveData<GameDetailsResponse>()
    val gameDetailsLiveData: LiveData<GameDetailsResponse> get() = _gameDetailsLiveData

    private val _errorLiveData = MutableLiveData<Throwable?>()
    val errorLiveData: LiveData<Throwable?> get() = _errorLiveData

    fun getGameDetails(gameId: Int) {
        _loadingLiveData.postValue(true)

        viewModelScope.launch {
            val getGameDetailsResponse = repository.getGameDetails(gameId)
            _loadingLiveData.postValue(false)

            if (getGameDetailsResponse is Success) {
                _gameDetailsLiveData.postValue(getGameDetailsResponse.data)
            } else {
                val failure = getGameDetailsResponse as Failure
                _errorLiveData.postValue(failure.error)
            }
        }
    }
}