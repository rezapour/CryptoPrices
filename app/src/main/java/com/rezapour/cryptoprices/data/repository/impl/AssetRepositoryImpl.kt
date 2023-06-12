package com.rezapour.cryptoprices.data.repository.impl

import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.data.database.DataBaseProvider
import com.rezapour.cryptoprices.data.exception.DataProviderException
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.model.Asset

class AssetRepositoryImpl constructor(
    private val apiProvider: ApiProvider,
    private val databaseProvider: DataBaseProvider
) : AssetRepository {
    override suspend fun getAssets(): DataState<List<Asset>> {
        return try {
            val assets = apiProvider.getAssets()
            databaseProvider.replaceAll(assets)
            DataState.Success(databaseProvider.getAllAssets())

        } catch (e: DataProviderException) {
            DataState.Error(e.messageId, databaseProvider.getAllAssets())
        } catch (e: Exception) {
            DataState.DefaultError(databaseProvider.getAllAssets())
        }
    }

    override suspend fun insertFavorite(asset: Asset) {
        databaseProvider.insertFavorite(asset)
    }

    override suspend fun getFavorite(): List<Asset> = databaseProvider.getFavorite()

    override suspend fun deleteFavorite(assetId: String) = databaseProvider.deleteFavorite(assetId)
    override suspend fun searchAsset(assetId: String): List<Asset> {
        return databaseProvider.searchAsset(assetId)
    }
}