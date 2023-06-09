package com.rezapour.cryptoprices.data.repository

import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.model.Asset

interface AssetRepository {

    suspend fun getAssets(): DataState<List<Asset>>
}