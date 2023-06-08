package com.rezapour.cryptoprices.data

import com.rezapour.cryptoprices.model.Asset

object SampleDataFactory {
    fun getAssets() =
        arrayListOf(
            Asset(
                assetId = "BTC",
                name = "Bitcoin",
                idIcon = "4caf2b16-a017-4e26-a348-2cea69c34cba"
            )
        )
}