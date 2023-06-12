package com.rezapour.cryptoprices.model

data class Asset(
    var assetId: String,
    var name: String,
    var imageUrl: String?,
    val favorite: Boolean = false
)