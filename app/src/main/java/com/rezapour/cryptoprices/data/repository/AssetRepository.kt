package com.rezapour.cryptoprices.data.repository

import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail

interface AssetRepository {

    suspend fun getAssets(): DataState<List<Asset>>

    suspend fun insertFavorite(asset: Asset)

    suspend fun getFavorite(): List<Asset>

    suspend fun deleteFavorite(assetId: String)

    suspend fun searchAsset(assetId: String): List<Asset>

    suspend fun getAssetDetail(assetIdBase: String, assetIdQuote: String = "EUR"): AssetDetail
}