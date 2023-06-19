package com.rezapour.cryptoprices.data.repository

import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail
import com.rezapour.cryptoprices.model.CacheableResult

interface AssetRepository {
    suspend fun getAssets()

    suspend fun searchAsset(assetId: String): List<Asset>

    suspend fun getAssetDetail(assetIdBase: String, assetIdQuote: String = "EUR"): CacheableResult<AssetDetail>
}