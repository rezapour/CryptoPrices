package com.rezapour.cryptoprices.view.asset_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.model.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetListViewModel @Inject constructor(private val repository: AssetRepository) :
    ViewModel() {

    private val _dataState: MutableStateFlow<DataState<List<Asset>>> = MutableStateFlow(
        DataState.Success(
            emptyList()
        )
    )

    init {
        loadData()
    }

    val dataState: StateFlow<DataState<List<Asset>>> = _dataState

    //Todo change dispatcher
    fun loadData() {
        _dataState.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _dataState.value = repository.getAssets()
        }
    }

    fun addFavorite(asset: Asset) {
        viewModelScope.launch(Dispatchers.IO) { repository.insertFavorite(asset) }
    }

    fun deleteFavorite(assetId: String) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteFavorite(assetId) }
    }

    //Todo empty
    fun getFavorite() {
        _dataState.value = DataState.Loading
        viewModelScope.launch { _dataState.value = DataState.Success(repository.getFavorite()) }
    }
}