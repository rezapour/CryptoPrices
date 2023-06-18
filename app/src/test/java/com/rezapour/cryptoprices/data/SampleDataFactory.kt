package com.rezapour.cryptoprices.data

import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.data.network.model.ExchangeRateNetworkEntity
import com.rezapour.cryptoprices.model.Asset

object SampleDataFactory {


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
            dataEnd = "2023-06-08",
            priceUsd = 0.0
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
            dataEnd = "2023-06-08",
            priceUsd = 26609.503079591109728377883359
        )

        return listOf(asset1, asset2)
    }

    fun getNetworkEntitySingleItem() = listOf(
        AssetNetworkEntity(
            assetId = "BTC",
            name = "Bitcoin",
            typeIsCrypto = 1,
            dataQuoteStart = "2014-02-24T17:43:05.0000000Z",
            dataQuoteEnd = "2023-06-08T00:00:00.0000000Z",
            dataOrderbookStart = "2014-02-24T17:43:05.0000000Z",
            dataOrderbookEnd = "2023-06-08T00:00:00.0000000Z",
            dataTradeStart = "2010-07-17T23:09:17.0000000Z",
            dataTradeEnd = "2023-06-08T00:00:00.0000000Z",
            dataSymbolsCount = 155669,
            volume1hrsUsd = 28644378161223972.00,
            volume1dayUsd = 1401705336720304751.01,
            volume1mthUsd = 133913054933902753258.66,
            idIcon = "4caf2b16-a017-4e26-a348-2cea69c34cba",
            dataStart = "2010-07-17",
            dataEnd = "2023-06-08",
            priceUsd = 26609.503079591109728377883359
        )
    )

    fun getNetworkEntity() = AssetNetworkEntity(
        assetId = "BTC",
        name = "Bitcoin",
        typeIsCrypto = 1,
        dataQuoteStart = "2014-02-24T17:43:05.0000000Z",
        dataQuoteEnd = "2023-06-08T00:00:00.0000000Z",
        dataOrderbookStart = "2014-02-24T17:43:05.0000000Z",
        dataOrderbookEnd = "2023-06-08T00:00:00.0000000Z",
        dataTradeStart = "2010-07-17T23:09:17.0000000Z",
        dataTradeEnd = "2023-06-08T00:00:00.0000000Z",
        dataSymbolsCount = 155669,
        volume1hrsUsd = 28644378161223972.00,
        volume1dayUsd = 1401705336720304751.01,
        volume1mthUsd = 133913054933902753258.66,
        idIcon = "4caf2b16-a017-4e26-a348-2cea69c34cba",
        dataStart = "2010-07-17",
        dataEnd = "2023-06-08",
        priceUsd = 26609.503079591109728377883359
    )

    fun getExchangeRateNetworkEntity() = ExchangeRateNetworkEntity(
        time = "2023-06-18T17:58:34.0000000Z",
        assetIdBase = "BTC",
        assetIdQuote = "EUR",
        rate = 24363.389421080892119244282009
    )


//    fun getAssetDataBaseEntity(): List<AssetDatabaseEntity> {
//        val asset1 = AssetDatabaseEntity(
//            assetId = "BTC",
//            name = "Bitcoin",
//            idIcon = "4caf2b16-a017-4e26-a348-2cea69c34cba"
//        )
//
//        val asset2 = AssetDatabaseEntity(
//            assetId = "LTC",
//            name = "Litecoin",
//            idIcon = "a201762f-1499-41ef-9b84-e0742cd00e48"
//        )
//
//        return listOf(asset1, asset2)
//    }

//    fun getAssetsTwoItems(): List<Asset> {
//        val asset1 = Asset(
//            assetId = "BTC",
//            name = "Bitcoin",
//            imageUrl = "4caf2b16-a017-4e26-a348-2cea69c34cba"
//        )
//
//        val asset2 = Asset(
//            assetId = "LTC",
//            name = "Litecoin",
//            imageUrl = "a201762f-1499-41ef-9b84-e0742cd00e48"
//        )
//
//        return listOf(asset1, asset2)
//    }
}