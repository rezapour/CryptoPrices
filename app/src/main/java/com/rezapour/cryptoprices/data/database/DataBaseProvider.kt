package com.rezapour.cryptoprices.data.database


import com.rezapour.cryptoprices.model.Asset

interface DataBaseProvider {

    suspend fun insertAll(vararg asset: Asset)

    suspend fun insertAll(assets: List<Asset>)

    suspend fun getAllAssets(): List<Asset>

    suspend fun replaceAll(assets: List<Asset>)

    suspend fun insertFavorite(asset: Asset)

    suspend fun getFavorite(): List<Asset>

    suspend fun deleteFavorite(assetId: String)

    suspend fun searchAsset(assetId:String):List<Asset>
}