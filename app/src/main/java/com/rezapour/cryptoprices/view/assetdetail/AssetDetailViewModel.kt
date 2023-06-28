package com.rezapour.cryptoprices.view.assetdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezapour.cryptoprices.util.UiState
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.model.AssetDetail
import com.rezapour.cryptoprices.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetDetailViewModel @Inject constructor(private val repository: AssetRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState<AssetDetail>> = MutableStateFlow(
        UiState.Loading
    )
    val uiState: StateFlow<UiState<AssetDetail>> = _uiState

    fun loadAssetDetail(assetId: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val dataState = repository.getAssetDetail(assetId)
            when (dataState) {
                is DataState.CacheResult -> _uiState.value = UiState.Error(dataState.data)
                is DataState.FreshResult -> _uiState.value = UiState.Success(dataState.data)
            }
        }
    }
}