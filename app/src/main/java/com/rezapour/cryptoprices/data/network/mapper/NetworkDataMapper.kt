package com.rezapour.cryptoprices.data.network.mapper

import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.data.network.model.ExchangeRateNetworkEntity
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail
import javax.inject.Inject

class NetworkDataMapper @Inject constructor() {

    private fun assetNetworkEntityToAsset(
        asset: AssetNetworkEntity,
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

    fun assetNetworkEntityListToAssetList(
        assets: List<AssetNetworkEntity>
    ): List<Asset> =
        assets.filter { assetNetworkEntity -> assetNetworkEntity.typeIsCrypto == 1 }
            .map { assetNetworkEntity ->
                assetNetworkEntityToAsset(
                    assetNetworkEntity
                )
            }

    fun assetDetailNetworkEntityToAssetDetail(
        asset: AssetNetworkEntity,
        exchange: ExchangeRateNetworkEntity
    ): AssetDetail =
        with(asset) {
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
                exchnageTime = exchange.time,
                assetIdQuote = exchange.assetIdQuote,
                rate = exchange.rate
            )
        }
}