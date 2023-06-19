package com.rezapour.cryptoprices.data.repository.impl

import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail
import com.rezapour.cryptoprices.model.CacheableResult

class AssetRepositoryImpl constructor(
    private val apiProvider: ApiProvider,
    private val dao: AssetDao,
    private val networkMapper: NetworkDataMapper,
    private val dbMapper: DataBaseMapper
) : AssetRepository {
    override suspend fun getAssets() {
        val assetNetworkEntity = apiProvider.getAssets()
        replaceAllAssets(networkMapper.assetNetworkEntityListToAssetList(assetNetworkEntity))
    }

    private suspend fun replaceAllAssets(assets: List<Asset>) {
        dao.deleteAll()
        dao.insertAll(dbMapper.assetListToAssetDatabaseEntityList(assets))
    }

    override suspend fun searchAsset(assetId: String): List<Asset> {
        return dbMapper.assetDatabaseEntityListToAssetList(dao.searchAsset(assetId))
    }

    override suspend fun getAssetDetail(
        assetIdBase: String,
        assetIdQuote: String
    ): CacheableResult<AssetDetail> {
        return try {
            val asset = apiProvider.getAssetDetail(assetIdBase)
            val exchangerate = apiProvider.getAssetPrice(assetIdBase, assetIdQuote)
            val assetDetail =
                networkMapper.assetDetailNetworkEntityToAssetDetail(asset, exchangerate)
            dao.updateAsset(dbMapper.assetDetailToAssetDataBaseEntity(assetDetail))
            CacheableResult.initFreshenResult(assetDetail)

        } catch (e: Exception) {
            CacheableResult.initCachedResult(
                dbMapper.assetDataBaseEntityToAssetDetail(
                    dao.searchAsset(
                        assetIdBase
                    ).first()
                )
            )
        }
    }
}