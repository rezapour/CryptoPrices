package com.rezapour.cryptoprices.view.view_models

import android.util.Log
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
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AssetDetailViewModel @Inject constructor(private val repository: AssetRepository) : ViewModel() {
    private val _dataState: MutableStateFlow<DataState<AssetDetail>> = MutableStateFlow(
        DataState.Loading
    )
    val dataState: StateFlow<DataState<AssetDetail>> = _dataState

    fun loadAssetDetail(assetId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _dataState.value = DataState.Success(repository.getAssetDetail(assetId))
            } catch (e: Exception) {
                Log.d("REZAPOUR","${e.message}")
                _dataState.value = DataState.DefaultError(null)
            }
        }
    }

}