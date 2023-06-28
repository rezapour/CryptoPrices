package com.rezapour.cryptoprices.view.assetlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezapour.cryptoprices.R
import com.rezapour.cryptoprices.util.UiState
import com.rezapour.cryptoprices.data.prefrence.SortState
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.data.repository.FavoriteRepository
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetItem
import com.rezapour.cryptoprices.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetListViewModel @Inject constructor(
    private val assetRepository: AssetRepository,
    private val favoriteRepository: FavoriteRepository,
    private val sortState: SortState
) :
    ViewModel() {

    var showSearchBar by mutableStateOf(sortState.getSearchState())
        private set
    var favoriteState by mutableStateOf(sortState.getFavoriteState())
        private set

    /*TODO: There is better to write custom save state
    link= https://www.youtube.com/watch?v=V-s4z7B_Gnc&t=2s
    https://developer.android.com/topic/libraries/architecture/saving-states
     */
    fun updateSearchState(boolean: Boolean) {
        showSearchBar = boolean
        sortState.setSearchState(showSearchBar)
    }

    fun updateFavoriteState(boolean: Boolean) {
        favoriteState = boolean
        sortState.setFavoriteState(favoriteState)
    }

    private val _uiState: MutableStateFlow<UiState<List<AssetItem>>> = MutableStateFlow(
        UiState.Loading
    )
    val uiState: StateFlow<UiState<List<AssetItem>>> = _uiState

    init {
        loadData()
    }

    fun cleanList() {
        _uiState.value = UiState.Success(emptyList())
    }

    fun loadData() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val dataState = assetRepository.getAssets()) {
                is DataState.CacheResult -> {
                    _uiState.value = UiState.Error(
                        mapToAssetItem(
                            dataState.data,
                            favoriteRepository.getFavoriteId()
                        )
                    )
                }

                is DataState.FreshResult -> {
                    _uiState.value = UiState.Success(
                        mapToAssetItem(
                            dataState.data,
                            favoriteRepository.getFavoriteId()
                        )
                    )
                }
            }
        }
    }

    fun addFavorite(asset: Asset) {
        viewModelScope.launch(Dispatchers.IO) { favoriteRepository.addFavorite(asset) }
    }

    fun deleteFavorite(assetId: String) {
        viewModelScope.launch(Dispatchers.IO) { favoriteRepository.deleteFavorite(assetId) }
    }

    fun getFavorite() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val assets = favoriteRepository.getFavorite()
            _uiState.value = UiState.Success(assets.map { favorite -> AssetItem(favorite, true) })

        }
    }

    fun search(assetId: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val assets = assetRepository.searchAsset(assetId)
            _uiState.value =
                UiState.Success(mapToAssetItem(assets, favoriteRepository.getFavoriteId()))
        }
    }

    private fun mapToAssetItem(assets: List<Asset>, favorite: Set<String>): List<AssetItem> =
        assets.map { asset -> AssetItem(asset, asset.assetId in favorite) }
}