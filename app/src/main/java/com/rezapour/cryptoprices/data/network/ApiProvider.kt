package com.rezapour.cryptoprices.data.network

import com.rezapour.cryptoprices.model.Asset

interface ApiProvider {
    suspend fun getAssets(): List<Asset>
}