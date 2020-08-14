package com.em4n0101.gamecollection.model

sealed class Result <out T: Any>

data class Success<out T:  Any>(val data: T) : Result<T>()

data class Failure(val error: Throwable?) : Result<Nothing>()