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

    fun loadData() {
        _dataState.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val cacheAble = assetRepository.getAssets()
            val assetItem = mapToAssetItem(
                cacheAble.data, favoriteRepository.getFavoriteId()
            )
            _dataState.value = calculateState(cacheAble.isCached!!, assetItem)
        }
    }

    fun addFavorite(asset: Asset) {
        viewModelScope.launch(Dispatchers.IO) { favoriteRepository.addFavorite(asset) }
    }

    fun deleteFavorite(assetId: String) {
        viewModelScope.launch(Dispatchers.IO) { favoriteRepository.deleteFavorite(assetId) }
    }

    fun getFavorite() {
        _dataState.value = DataState.Loading
        viewModelScope.launch {
            val assets = favoriteRepository.getFavorite()
            if (assets.isNotEmpty()) {
                _dataState.value =
                    DataState.Success(assets.map { favorite -> AssetItem(favorite, true) })
            } else _dataState.value = DataState.EmptyList("there is noting to show.")
        }
    }

    fun search(assetId: String) {
        _dataState.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val assets = assetRepository.searchAsset(assetId)
            if (assets.isNotEmpty())
                _dataState.value = DataState.Success(
                    mapToAssetItem(
                        assets,
                        favoriteRepository.getFavoriteId()
                    )
                )
            else _dataState.value = DataState.EmptyList("There is nothing to show.")
        }
    }

    private fun mapToAssetItem(assets: List<Asset>, favorite: Set<String>): List<AssetItem> =
        assets.map { asset -> AssetItem(asset, asset.assetId in favorite) }

    private fun calculateState(cacheAble: Boolean, assetItem: List<AssetItem>) =
        if (!cacheAble) {
            DataState.Success(assetItem)
        } else {
            if (assetItem.isEmpty())
                DataState.EmptyList("List Is empty")
            else
                DataState.Error(assetItem)
        }
}