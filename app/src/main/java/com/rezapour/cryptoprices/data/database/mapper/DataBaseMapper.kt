package com.rezapour.cryptoprices.data.database.mapper

import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.model.Asset
import javax.inject.Inject

class DataBaseMapper @Inject constructor() {

    private fun assetDatabaseEntityToAsset(asset: AssetDatabaseEntity): Asset =
        with(asset) {
            Asset(
                assetId = assetId,
                name = name,
                idIcon = idIcon
            )
        }

    fun assetDatabaseEntityListToAssetList(assets: List<AssetDatabaseEntity>): List<Asset> =
        assets.map { assetDatabaseEntity -> assetDatabaseEntityToAsset(assetDatabaseEntity) }


    private fun assetToAssetDatabaseEntity(asset: Asset): AssetDatabaseEntity =
        with(asset) {
            AssetDatabaseEntity(
                assetId = assetId,
                name = name,
                idIcon = idIcon
            )
        }

    fun assetListToAssetDatabaseEntityList(assets: List<Asset>): List<AssetDatabaseEntity> =
        assets.map { asset -> assetToAssetDatabaseEntity(asset) }
}