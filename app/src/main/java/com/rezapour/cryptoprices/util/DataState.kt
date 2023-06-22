package com.rezapour.cryptoprices.util

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val data: T) : DataState<T>()

    data class EmptyList(val messageId: Int) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}