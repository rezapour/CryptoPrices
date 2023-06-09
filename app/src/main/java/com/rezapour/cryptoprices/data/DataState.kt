package com.rezapour.cryptoprices.data

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val message: Int, val data: T) : DataState<T>()
    data class DefaultError<T>(val data: T) : DataState<T>()
    object Loading : DataState<Nothing>()
}