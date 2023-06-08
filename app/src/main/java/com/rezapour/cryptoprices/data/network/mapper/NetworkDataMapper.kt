package com.rezapour.cryptoprices.data.network.mapper

import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.model.Asset
import javax.inject.Inject

class NetworkDataMapper @Inject constructor() {

    private fun AssetNetwrokEntityToAsset(asset: AssetNetworkEntity): Asset =
        with(asset) {
            Asset(
                assetId = assetId,
                name = name,
                idIcon = idIcon
            )
        }

    fun AssetNetworkEntityListToAssetList(assets: List<AssetNetworkEntity>): List<Asset> =
        assets.filter { assetNetworkEntity -> assetNetworkEntity.typeIsCrypto == 1 }
            .map { assetNetworkEntity -> AssetNetwrokEntityToAsset(assetNetworkEntity) }
}