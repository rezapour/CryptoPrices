package com.rezapour.cryptoprices.data.repository

import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail
import com.rezapour.cryptoprices.model.CacheableResult

interface AssetRepository {
    suspend fun getAssets(): CacheableResult<List<Asset>>

    suspend fun searchAsset(assetId: String): List<Asset>

    suspend fun getAssetDetail(assetIdBase: String, assetIdQuote: String = "EUR"): AssetDetail
//    suspend fun getAssets(page: Int): CacheableResult<List<Asset>>
}