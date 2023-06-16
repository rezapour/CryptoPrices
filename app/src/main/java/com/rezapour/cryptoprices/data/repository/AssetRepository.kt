package com.rezapour.cryptoprices.data.repository

import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail

interface AssetRepository {
    suspend fun getAssets(): List<Asset>

    suspend fun searchAsset(assetId: String): List<Asset>

    suspend fun getAssetDetail(assetIdBase: String, assetIdQuote: String = "EUR"): AssetDetail
}