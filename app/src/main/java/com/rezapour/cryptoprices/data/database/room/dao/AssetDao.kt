package com.rezapour.cryptoprices.data.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity

@Dao
interface AssetDao {
    @Insert
    suspend fun insertAll(vararg asset: AssetDatabaseEntity)

    @Insert
    suspend fun insertAll(assets: List<AssetDatabaseEntity>)

    @Query("SELECT * FROM asset")
    fun getAllAssets(): List<AssetDatabaseEntity>
}