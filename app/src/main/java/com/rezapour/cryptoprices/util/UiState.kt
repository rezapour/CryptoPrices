package com.rezapour.cryptoprices.util

import com.rezapour.cryptoprices.model.AssetItem

sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val data: T) : UiState<T>()
    object Loading : UiState<Nothing>()
}