package com.rezapour.cryptoprices.data.network.mapper

import com.rezapour.cryptoprices.data.network.model.AssetIconNetWorkEntity
import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.data.network.model.ExchangeRateNetworkEntity
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetDetail
import javax.inject.Inject

class NetworkDataMapper @Inject constructor() {

    private fun assetNetworkEntityToAsset(
        asset: AssetNetworkEntity,
        icon: AssetIconNetWorkEntity?
    ): Asset =
        with(asset) {
            Asset(
                assetId = assetId,
                name = name,
                imageUrl = icon?.url
            )
        }

    fun assetNetworkEntityListToAssetList(
        assets: List<AssetNetworkEntity>,
        icons: List<AssetIconNetWorkEntity>
    ): List<Asset> =
        assets.filter { assetNetworkEntity -> assetNetworkEntity.typeIsCrypto == 1 }
            .map { assetNetworkEntity ->
                assetNetworkEntityToAsset(
                    assetNetworkEntity,
                    icons.find { icon -> icon.assetId == assetNetworkEntity.assetId })
            }


    fun assetNetworkEntityToAssetDetail(
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
                dataEnd = dataEnd,
                exchnageTime = exchange.time,
                assetIdQuote = exchange.assetIdQuote,
                rate = exchange.rate
            )
        }
}