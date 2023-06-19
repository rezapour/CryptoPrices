package com.rezapour.cryptoprices.view.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.model.AssetDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AssetDetailViewModel @Inject constructor(private val repository: AssetRepository) :
    ViewModel() {
    private val _dataState: MutableStateFlow<DataState<AssetDetail>> = MutableStateFlow(
        DataState.Loading
    )
    val dataState: StateFlow<DataState<AssetDetail>> = _dataState

    fun loadAssetDetail(assetId: String) {
        _dataState.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val cacheableResult = repository.getAssetDetail(assetId)
            if (cacheableResult.isCached)
                _dataState.value = DataState.Error(cacheableResult.data)
            else
                _dataState.value = DataState.Success(cacheableResult.data)
        }
    }
}