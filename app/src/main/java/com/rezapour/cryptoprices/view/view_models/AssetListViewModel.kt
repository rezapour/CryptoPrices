package com.rezapour.cryptoprices.view.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.rezapour.cryptoprices.R
import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.prefrence.SortState
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.data.repository.FavoriteRepository
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetListViewModel @Inject constructor(
    private val assetRepository: AssetRepository,
    private val favoriteRepository: FavoriteRepository,
    private val mapper: DataBaseMapper,
    private val sortState: SortState,
    page: Pager<Int, AssetDatabaseEntity>
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

    private val _dataState: MutableStateFlow<DataState<List<AssetItem>>> = MutableStateFlow(
        DataState.Success(
            emptyList()
        )
    )
    val dataState: StateFlow<DataState<List<AssetItem>>> = _dataState

    val assetPagingFlow = page
        .flow
        .map { pagingData ->
            pagingData.map { asset ->
                AssetItem(
                    mapper.assetDatabaseEntityToAsset(asset),
                    asset.assetId in favoriteRepository.getFavoriteId()
                )
            }
        }
        .cachedIn(viewModelScope)

    fun addFavorite(asset: Asset) {
        viewModelScope.launch(Dispatchers.IO) { favoriteRepository.addFavorite(asset) }
    }

    fun deleteFavorite(assetId: String) {
        viewModelScope.launch(Dispatchers.IO) { favoriteRepository.deleteFavorite(assetId) }
    }

    fun getFavorite() {
        _dataState.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val assets = favoriteRepository.getFavorite()
            if (assets.isNotEmpty()) {
                _dataState.value =
                    DataState.Success(assets.map { favorite -> AssetItem(favorite, true) })
            } else _dataState.value = DataState.EmptyList(R.string.empty_list)
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
            else _dataState.value = DataState.EmptyList(R.string.empty_list)
        }
    }

    private fun mapToAssetItem(assets: List<Asset>, favorite: Set<String>): List<AssetItem> =
        assets.map { asset -> AssetItem(asset, asset.assetId in favorite) }

}