package com.rezapour.cryptoprices.data.network

import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail

interface ApiProvider {
    suspend fun getAssets(): List<AssetNetworkEntity>

    suspend fun getAssetDetail(assetIdBase: String, assetIdQuote: String): AssetDetail
}