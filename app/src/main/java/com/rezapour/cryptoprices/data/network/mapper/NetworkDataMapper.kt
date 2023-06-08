package com.rezapour.cryptoprices.data.network.mapper

import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.model.Asset
import javax.inject.Inject

class NetworkDataMapper @Inject constructor() {

    private fun assetNetwrokEntityToAsset(asset: AssetNetworkEntity): Asset =
        with(asset) {
            Asset(
                assetId = assetId,
                name = name,
                idIcon = idIcon
            )
        }

    fun assetNetworkEntityListToAssetList(assets: List<AssetNetworkEntity>): List<Asset> =
        assets.filter { assetNetworkEntity -> assetNetworkEntity.typeIsCrypto == 1 }
            .map { assetNetworkEntity -> assetNetwrokEntityToAsset(assetNetworkEntity) }
}