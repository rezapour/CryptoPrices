package com.rezapour.cryptoprices.util

sealed class DataState<out T> {

    data class FreshResult<T>(val data: T) : DataState<T>()
    data class CacheResult<T>(val data: T) : DataState<T>()

}