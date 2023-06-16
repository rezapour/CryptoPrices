package com.rezapour.cryptoprices.data.repository.impl

import android.util.Log
import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail

class AssetRepositoryImpl constructor(
    private val apiProvider: ApiProvider,
    private val dao: AssetDao,
    private val networkMapper: NetworkDataMapper,
    private val dbMapper: DataBaseMapper
) : AssetRepository {
    //TODO how to find out is
    override suspend fun getAssets(): List<Asset> {
        return try {
            val assetNetworkEntity = apiProvider.getAssets()
            replaceAllAssets(networkMapper.assetNetworkEntityListToAssetList(assetNetworkEntity))
            dbMapper.assetDatabaseEntityListToAssetList(dao.getAllAssets())

        } catch (e: Exception) {
            Log.d("REZAAPP",e.message.toString())
            dbMapper.assetDatabaseEntityListToAssetList(dao.getAllAssets())
        }
    }

    private suspend fun replaceAllAssets(assets: List<Asset>) {
        dao.deleteAll()
        dao.insertAll(dbMapper.assetListToAssetDatabaseEntityList(assets))
    }

    override suspend fun searchAsset(assetId: String): List<Asset> {
        return dbMapper.assetDatabaseEntityListToAssetList(dao.searchAsset(assetId))
    }

    override suspend fun getAssetDetail(assetIdBase: String, assetIdQuote: String): AssetDetail {
        return apiProvider.getAssetDetail(assetIdBase, assetIdQuote)
    }
}