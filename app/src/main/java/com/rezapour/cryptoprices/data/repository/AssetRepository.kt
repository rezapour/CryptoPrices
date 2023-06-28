package com.rezapour.cryptoprices.data.repository

import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail
import com.rezapour.cryptoprices.util.DataState


interface AssetRepository {
    suspend fun getAssets(): DataState<List<Asset>>

    suspend fun searchAsset(assetId: String): List<Asset>

    suspend fun getAssetDetail(
        assetIdBase: String,
        assetIdQuote: String = "EUR"
    ): DataState<AssetDetail>
}