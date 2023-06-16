package com.rezapour.cryptoprices.model

data class Asset(
    var assetId: String,
    var name: String,
    var idIcon: String?,
    var dataQuoteStart: String?,
    var dataQuoteEnd: String?,
    var dataOrderbookStart: String?,
    var dataOrderbookEnd: String?,
    var dataTradeStart: String?,
    var dataTradeEnd: String?,
    var dataSymbolsCount: Int,
    var volume1hrsUsd: Double,
    var volume1dayUsd: Double,
    var volume1mthUsd: Double,
    var priceUsd: Double,
    var dataStart: String?,
    var dataEnd: String?
)

data class AssetDetail(
    var assetId: String,
    var name: String,
    var idIcon: String?,
    var dataQuoteStart: String?,
    var dataQuoteEnd: String?,
    var dataOrderbookStart: String?,
    var dataOrderbookEnd: String?,
    var dataTradeStart: String?,
    var dataTradeEnd: String?,
    var dataSymbolsCount: Int,
    var volume1hrsUsd: Double,
    var volume1dayUsd: Double,
    var volume1mthUsd: Double,
    var priceUsd: Double,
    var dataStart: String?,
    var dataEnd: String?,
    var exchnageTime: String,
    var assetIdQuote: String,
    var rate: Double
)

data class AssetItem(var asset: Asset, var favorite: Boolean)


