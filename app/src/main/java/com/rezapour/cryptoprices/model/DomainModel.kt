package com.rezapour.cryptoprices.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Asset(
    var assetId: String,
    var name: String,
    var idIcon: String?,
    val favorite: Boolean = false
)