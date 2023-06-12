package com.rezapour.cryptoprices.data.database.mapper

import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.database.room.entities.AssetFavoriteDataBaseEntity
import com.rezapour.cryptoprices.model.Asset
import javax.inject.Inject

class DataBaseMapper @Inject constructor() {

    private fun assetDatabaseEntityToAsset(
        asset: AssetDatabaseEntity,
        favorite: Boolean = false
    ): Asset =
        with(asset) {
            Asset(
                assetId = assetId,
                name = name,
                imageUrl = idIcon,
                favorite = favorite
            )
        }

    //TODO make it cleaner
    fun assetDatabaseEntityListToAssetList(
        assets: List<AssetDatabaseEntity>,
        favorites: List<AssetFavoriteDataBaseEntity>
    ): List<Asset> =
        assets.map { assetDatabaseEntity ->
            assetDatabaseEntityToAsset(
                assetDatabaseEntity, favorites.contains(
                    AssetFavoriteDataBaseEntity(
                        assetDatabaseEntity.assetId,
                        assetDatabaseEntity.name,
                        assetDatabaseEntity.idIcon
                    )
                )
            )
        }


    private fun assetToAssetDatabaseEntity(asset: Asset): AssetDatabaseEntity =
        with(asset) {
            AssetDatabaseEntity(
                assetId = assetId,
                name = name,
                idIcon = imageUrl
            )
        }

    fun assetListToAssetDatabaseEntityList(assets: List<Asset>): List<AssetDatabaseEntity> =
        assets.map { asset -> assetToAssetDatabaseEntity(asset) }


    fun assetFavoriteDatabaseEntityToAsset(asset: AssetFavoriteDataBaseEntity): Asset =
        with(asset) {
            Asset(
                assetId = assetId,
                name = name,
                imageUrl = idIcon,
                favorite = true
            )
        }

    fun assetToAssetFavoriteDatabaseEntity(asset: Asset): AssetFavoriteDataBaseEntity =
        with(asset) {
            AssetFavoriteDataBaseEntity(
                assetId = assetId,
                name = name,
                idIcon = imageUrl
            )
        }

    fun assetFavoriteDatabaseEntityListToAssetList(assets: List<AssetFavoriteDataBaseEntity>): List<Asset> =
        assets.map { assetDatabaseEntity -> assetFavoriteDatabaseEntityToAsset(assetDatabaseEntity) }
}