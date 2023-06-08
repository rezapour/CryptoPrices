package com.rezapour.cryptoprices.data.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asset")
class AssetDatabaseEntity(
    @PrimaryKey @ColumnInfo(name = "asset_Id") val assetId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "id_icon") val idIcon: String
)