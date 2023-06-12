package com.rezapour.cryptoprices.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AssetNetworkEntity(
    @Expose @SerializedName("asset_id") var assetId: String,
    @Expose @SerializedName("name") var name: String,
    @Expose @SerializedName("type_is_crypto") var typeIsCrypto: Int,
    @Expose @SerializedName("data_quote_start") var dataQuoteStart: String,
    @Expose @SerializedName("data_quote_end") var dataQuoteEnd: String,
    @Expose @SerializedName("data_orderbook_start") var dataOrderbookStart: String,
    @Expose @SerializedName("data_orderbook_end") var dataOrderbookEnd: String,
    @Expose @SerializedName("data_trade_start") var dataTradeStart: String,
    @Expose @SerializedName("data_trade_end") var dataTradeEnd: String,
    @Expose @SerializedName("data_symbols_count") var dataSymbolsCount: Int,
    @Expose @SerializedName("volume_1hrs_usd") var volume1hrsUsd: Double,
    @Expose @SerializedName("volume_1day_usd") var volume1dayUsd: Double,
    @Expose @SerializedName("volume_1mth_usd") var volume1mthUsd: Double,
    @Expose @SerializedName("id_icon") var idIcon: String?,
    @Expose @SerializedName("data_start") var dataStart: String,
    @Expose @SerializedName("data_end") var dataEnd: String
)