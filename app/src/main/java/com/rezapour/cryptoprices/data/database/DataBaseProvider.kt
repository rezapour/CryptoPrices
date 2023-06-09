package com.rezapour.cryptoprices.data.database


import com.rezapour.cryptoprices.model.Asset

interface DataBaseProvider {

    suspend fun insertAll(vararg asset: Asset)

    suspend fun insertAll(assets: List<Asset>)

    fun getAllAssets(): List<Asset>

    suspend fun replaceAll(assets: List<Asset>)
}