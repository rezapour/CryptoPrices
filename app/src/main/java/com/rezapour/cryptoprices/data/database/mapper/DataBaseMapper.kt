package com.rezapour.cryptoprices.data.database.mapper

import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.database.room.entities.FavoriteDataBaseEntity
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
                idIcon = idIcon,
                dataQuoteStart = dataQuoteStart,
                dataQuoteEnd = dataQuoteEnd,
                dataOrderbookStart = dataOrderbookStart,
                dataOrderbookEnd = dataOrderbookEnd,
                dataTradeStart = dataTradeStart,
                dataTradeEnd = dataTradeEnd,
                dataSymbolsCount = dataSymbolsCount,
                volume1hrsUsd = volume1hrsUsd,
                volume1dayUsd = volume1dayUsd,
                volume1mthUsd = volume1mthUsd,
                priceUsd = priceUsd,
                dataStart = dataStart,
                dataEnd = dataEnd
            )
        }

    fun assetDatabaseEntityListToAssetList(
        assets: List<AssetDatabaseEntity>
    ): List<Asset> =
        assets.map { assetDatabaseEntity ->
            assetDatabaseEntityToAsset(assetDatabaseEntity)
        }


    private fun assetToAssetDatabaseEntity(asset: Asset): AssetDatabaseEntity =
        with(asset) {
            AssetDatabaseEntity(
                assetId = assetId,
                name = name,
                idIcon = idIcon,
                dataQuoteStart = dataQuoteStart,
                dataQuoteEnd = dataQuoteEnd,
                dataOrderbookStart = dataOrderbookStart,
                dataOrderbookEnd = dataOrderbookEnd,
                dataTradeStart = dataTradeStart,
                dataTradeEnd = dataTradeEnd,
                dataSymbolsCount = dataSymbolsCount,
                volume1hrsUsd = volume1hrsUsd,
                volume1dayUsd = volume1dayUsd,
                volume1mthUsd = volume1mthUsd,
                priceUsd = priceUsd,
                dataStart = dataStart,
                dataEnd = dataEnd
            )
        }

    fun assetListToAssetDatabaseEntityList(assets: List<Asset>): List<AssetDatabaseEntity> =
        assets.map { asset -> assetToAssetDatabaseEntity(asset) }

    fun favoriteDatabaseEntityToAsset(
        asset: FavoriteDataBaseEntity
    ): Asset =
        with(asset) {
            Asset(
                assetId = assetId,
                name = name,
                idIcon = idIcon,
                dataQuoteStart = dataQuoteStart,
                dataQuoteEnd = dataQuoteEnd,
                dataOrderbookStart = dataOrderbookStart,
                dataOrderbookEnd = dataOrderbookEnd,
                dataTradeStart = dataTradeStart,
                dataTradeEnd = dataTradeEnd,
                dataSymbolsCount = dataSymbolsCount,
                volume1hrsUsd = volume1hrsUsd,
                volume1dayUsd = volume1dayUsd,
                volume1mthUsd = volume1mthUsd,
                priceUsd = priceUsd,
                dataStart = dataStart,
                dataEnd = dataEnd
            )
        }

    fun assetToFavoriteDatabaseEntity(
        asset: Asset
    ): FavoriteDataBaseEntity =
        with(asset) {
            FavoriteDataBaseEntity(
                assetId = assetId,
                name = name,
                idIcon = idIcon,
                dataQuoteStart = dataQuoteStart,
                dataQuoteEnd = dataQuoteEnd,
                dataOrderbookStart = dataOrderbookStart,
                dataOrderbookEnd = dataOrderbookEnd,
                dataTradeStart = dataTradeStart,
                dataTradeEnd = dataTradeEnd,
                dataSymbolsCount = dataSymbolsCount,
                volume1hrsUsd = volume1hrsUsd,
                volume1dayUsd = volume1dayUsd,
                volume1mthUsd = volume1mthUsd,
                priceUsd = priceUsd,
                dataStart = dataStart,
                dataEnd = dataEnd
            )
        }

    fun favoriteDatabaseEntityListToAssetList(favoriteList: List<FavoriteDataBaseEntity>): List<Asset> =
        favoriteList.map { favorite -> favoriteDatabaseEntityToAsset(favorite) }


}