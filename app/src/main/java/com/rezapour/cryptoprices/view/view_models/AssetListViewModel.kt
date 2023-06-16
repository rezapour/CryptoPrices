package com.rezapour.cryptoprices.view.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.data.repository.FavoriteRepository
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetListViewModel @Inject constructor(
    private val assetRepository: AssetRepository,
    private val favoriteRepository: FavoriteRepository
) :
    ViewModel() {

    private val _dataState: MutableStateFlow<DataState<List<AssetItem>>> = MutableStateFlow(
        DataState.Success(
            emptyList()
        )
    )
    val dataState: StateFlow<DataState<List<AssetItem>>> = _dataState

    init {
        loadData()
    }

    //Todo change dispatcher and find a way for error
    fun loadData() {
        _dataState.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _dataState.value = DataState.Success(
                mapToAssetItem(
                    assetRepository.getAssets(),
                    favoriteRepository.getFavoriteId()
                )
            )
        }
    }

    fun addFavorite(asset: Asset) {
        viewModelScope.launch(Dispatchers.IO) { favoriteRepository.addFavorite(asset) }
    }

    fun deleteFavorite(assetId: String) {
        viewModelScope.launch(Dispatchers.IO) { favoriteRepository.deleteFavorite(assetId) }
    }

    //Todo emptyList message
    fun getFavorite() {
        _dataState.value = DataState.Loading
        viewModelScope.launch {
            _dataState.value = DataState.Success(
                favoriteRepository.getFavorite().map { favorite -> AssetItem(favorite, true) })
        }
    }

    //Todo emptyList message
    fun search(assetId: String) {
        _dataState.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _dataState.value = DataState.Success(
                mapToAssetItem(
                    assetRepository.searchAsset(assetId),
                    favoriteRepository.getFavoriteId()
                )
            )
        }
    }

    private fun mapToAssetItem(assets: List<Asset>, favorite: Set<String>): List<AssetItem> =
        assets.map { asset -> AssetItem(asset, asset.assetId in favorite) }


}