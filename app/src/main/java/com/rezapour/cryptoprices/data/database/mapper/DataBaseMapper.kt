package com.rezapour.cryptoprices.data.database.mapper

import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.database.room.entities.FavoriteDataBaseEntity
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail
import javax.inject.Inject

class DataBaseMapper @Inject constructor() {

     fun assetDatabaseEntityToAsset(
        asset: AssetDatabaseEntity
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

    fun assetDataBaseEntityToAssetDetail(asset: AssetDatabaseEntity): AssetDetail = with(asset) {
        AssetDetail(
            assetId = assetId,
            name = name,
            idIcon = idIcon,
            dataQuoteStart = dataQuoteStart,
            dataQuoteEnd = dataQuoteEnd,
            dataOrderBookStart = dataOrderbookStart,
            dataOrderBookEnd = dataOrderbookEnd,
            dataTradeStart = dataTradeStart,
            dataTradeEnd = dataTradeEnd,
            dataSymbolsCount = dataSymbolsCount,
            volume1hrsUsd = volume1hrsUsd,
            volume1dayUsd = volume1dayUsd,
            volume1mthUsd = volume1mthUsd,
            priceUsd = priceUsd,
            dataStart = dataStart,
            dataEnd = dataEnd,
            assetIdQuote = assetIdQuote,
            exchangeTime = exchangeTime,
            rate = rate
        )
    }

    fun assetDetailToAssetDataBaseEntity(asset: AssetDetail): AssetDatabaseEntity = with(asset) {
        AssetDatabaseEntity(
            assetId = assetId,
            name = name,
            idIcon = idIcon,
            dataQuoteStart = dataQuoteStart,
            dataQuoteEnd = dataQuoteEnd,
            dataOrderbookStart = dataOrderBookStart,
            dataOrderbookEnd = dataOrderBookEnd,
            dataTradeStart = dataTradeStart,
            dataTradeEnd = dataTradeEnd,
            dataSymbolsCount = dataSymbolsCount,
            volume1hrsUsd = volume1hrsUsd,
            volume1dayUsd = volume1dayUsd,
            volume1mthUsd = volume1mthUsd,
            priceUsd = priceUsd,
            dataStart = dataStart,
            dataEnd = dataEnd,
            assetIdQuote = assetIdQuote,
            exchangeTime = exchangeTime,
            rate = rate
        )
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

    private fun favoriteDatabaseEntityToAsset(
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