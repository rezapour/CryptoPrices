package com.rezapour.cryptoprices.data.network.mapper

import com.rezapour.cryptoprices.data.network.model.AssetIconNetWorkEntity
import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.model.Asset
import javax.inject.Inject

class NetworkDataMapper @Inject constructor() {

    private fun assetNetwrokEntityToAsset(
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
                assetNetwrokEntityToAsset(
                    assetNetworkEntity,
                    icons.find { icon -> icon.assetId == assetNetworkEntity.assetId })
            }
}