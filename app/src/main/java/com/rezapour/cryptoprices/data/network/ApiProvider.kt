package com.rezapour.cryptoprices.data.network

import com.rezapour.cryptoprices.data.network.model.AssetIconNetWorkEntity
import com.rezapour.cryptoprices.model.Asset

interface ApiProvider {
    suspend fun getAssets(): List<Asset>

}