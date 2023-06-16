package com.rezapour.cryptoprices.data.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteDataBaseEntity(
    @PrimaryKey @ColumnInfo("asset_id") var assetId: String,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("data_quote_start") var dataQuoteStart: String?,
    @ColumnInfo("data_quote_end") var dataQuoteEnd: String?,
    @ColumnInfo("data_orderbook_start") var dataOrderbookStart: String?,
    @ColumnInfo("data_orderbook_end") var dataOrderbookEnd: String?,
    @ColumnInfo("data_trade_start") var dataTradeStart: String?,
    @ColumnInfo("data_trade_end") var dataTradeEnd: String?,
    @ColumnInfo("data_symbols_count") var dataSymbolsCount: Int,
    @ColumnInfo("volume_1hrs_usd") var volume1hrsUsd: Double,
    @ColumnInfo("volume_1day_usd") var volume1dayUsd: Double,
    @ColumnInfo("volume_1mth_usd") var volume1mthUsd: Double,
    @ColumnInfo("price_usd") var priceUsd: Double,
    @ColumnInfo("id_icon") var idIcon: String?,
    @ColumnInfo("data_start") var dataStart: String?,
    @ColumnInfo("data_end") var dataEnd: String?
)
