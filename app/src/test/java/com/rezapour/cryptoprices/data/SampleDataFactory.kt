package com.rezapour.cryptoprices.data

import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.model.Asset

object SampleDataFactory {
    fun getAssets() =
        arrayListOf(
            Asset(
                assetId = "BTC",
                name = "Bitcoin",
                imageUrl = "4caf2b16-a017-4e26-a348-2cea69c34cba"
            )
        )


    fun getAssetsNetworkEntity(): List<AssetNetworkEntity> {
        val asset1 = AssetNetworkEntity(
            assetId = "USD",
            name = "US Dollar",
            typeIsCrypto = 0,
            dataQuoteStart = "2014-02-24T17:43:05.0000000Z",
            dataQuoteEnd = "2014-02-24T17:43:05.0000000Z",
            dataOrderbookStart = "2014-02-24T17:43:05.0000000Z",
            dataOrderbookEnd = "2014-02-24T17:43:05.0000000Z",
            dataTradeStart = "2014-02-24T17:43:05.0000000Z",
            dataTradeEnd = "2014-02-24T17:43:05.0000000Z",
            dataSymbolsCount = 187129,
            volume1hrsUsd = 2906354384430.67,
            volume1dayUsd = 2906354384430.67,
            volume1mthUsd = 2906354384430.67,
            idIcon = "0a4185f2-1a03-4a7c-b866-ba7076d8c73b",
            dataStart = "2010-07-17",
            dataEnd = "2023-06-08"
        )

        val asset2 = AssetNetworkEntity(
            assetId = "BTC",
            name = "Bitcoin",
            typeIsCrypto = 1,
            dataQuoteStart = "2014-02-24T17:43:05.0000000Z",
            dataQuoteEnd = "2014-02-24T17:43:05.0000000Z",
            dataOrderbookStart = "2014-02-24T17:43:05.0000000Z",
            dataOrderbookEnd = "2014-02-24T17:43:05.0000000Z",
            dataTradeStart = "2014-02-24T17:43:05.0000000Z",
            dataTradeEnd = "2014-02-24T17:43:05.0000000Z",
            dataSymbolsCount = 187129,
            volume1hrsUsd = 2906354384430.67,
            volume1dayUsd = 2906354384430.67,
            volume1mthUsd = 2906354384430.67,
            idIcon = "4caf2b16-a017-4e26-a348-2cea69c34cba",
            dataStart = "2010-07-17",
            dataEnd = "2023-06-08"
        )

        return listOf(asset1, asset2)
    }

    fun getAssetDataBaseEntity(): List<AssetDatabaseEntity> {
        val asset1 = AssetDatabaseEntity(
            assetId = "BTC",
            name = "Bitcoin",
            idIcon = "4caf2b16-a017-4e26-a348-2cea69c34cba"
        )

        val asset2 = AssetDatabaseEntity(
            assetId = "LTC",
            name = "Litecoin",
            idIcon = "a201762f-1499-41ef-9b84-e0742cd00e48"
        )

        return listOf(asset1, asset2)
    }

    fun getAssetsTwoItems(): List<Asset> {
        val asset1 = Asset(
            assetId = "BTC",
            name = "Bitcoin",
            imageUrl = "4caf2b16-a017-4e26-a348-2cea69c34cba"
        )

        val asset2 = Asset(
            assetId = "LTC",
            name = "Litecoin",
            imageUrl = "a201762f-1499-41ef-9b84-e0742cd00e48"
        )

        return listOf(asset1, asset2)
    }
}