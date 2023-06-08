package com.rezapour.cryptoprices.data.database.impl

import com.rezapour.cryptoprices.data.database.DataBaseProvider
import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.model.Asset

class DataBaseProviderImpl(private val dao: AssetDao, private val mapper: DataBaseMapper) :
    DataBaseProvider {
    override suspend fun insertAll(vararg assets: Asset) {
        dao.insertAll(mapper.assetListToAssetDatabaseEntityList(listOf(*assets)))
    }

    override suspend fun insertAll(assets: List<Asset>) {
        dao.insertAll(mapper.assetListToAssetDatabaseEntityList(assets))
    }

    override fun getAllAssets(): List<Asset> =
        mapper.assetDatabaseEntityListToAssetList(dao.getAllAssets())
}