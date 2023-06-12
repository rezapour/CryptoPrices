package com.rezapour.cryptoprices.data.database.impl

import com.rezapour.cryptoprices.data.database.DataBaseProvider
import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.model.Asset

class DataBaseProviderImpl(private val dao: AssetDao, private val mapper: DataBaseMapper) :
    DataBaseProvider {
    override suspend fun insertAll(vararg assets: Asset) {
        insertAll(*assets)
    }

    override suspend fun insertAll(assets: List<Asset>) {
        dao.insertAll(mapper.assetListToAssetDatabaseEntityList(assets))
    }

    override suspend fun getAllAssets(): List<Asset> {
        return mapper.assetDatabaseEntityListToAssetList(dao.getAllAssets(), dao.getFavorite())
    }

    override suspend fun replaceAll(assets: List<Asset>) {
        dao.deleteAll()
        dao.insertAll(mapper.assetListToAssetDatabaseEntityList(assets))
    }

    override suspend fun insertFavorite(asset: Asset) {
        dao.insertFavorite(mapper.assetToAssetFavoriteDatabaseEntity(asset))
    }

    override suspend fun getFavorite(): List<Asset> =
        mapper.assetFavoriteDatabaseEntityListToAssetList(dao.getFavorite())

    override suspend fun deleteFavorite(assetId: String) {
        dao.deleteFavorite(assetId)
    }
}