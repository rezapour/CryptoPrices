package com.rezapour.cryptoprices.data

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val data: T) : DataState<T>()

    data class EmptyList(val message: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}