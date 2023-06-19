package com.rezapour.cryptoprices.data.database.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity

@Dao
interface AssetDao {
    @Insert
    suspend fun insertAll(assets: List<AssetDatabaseEntity>)

    @Query("SELECT * FROM asset")
    fun getAllAssets(): List<AssetDatabaseEntity>

    @Query("DELETE FROM asset")
    suspend fun deleteAll()

    @Query("SELECT * FROM asset WHERE asset_Id LIKE '%' || :assetId || '%'")
    fun searchAsset(assetId: String): List<AssetDatabaseEntity>

    @Update
    fun updateAsset(asset: AssetDatabaseEntity)

    @Query("SELECT * FROM asset")
    fun getAllAssetsTest(): PagingSource<Int, AssetDatabaseEntity>
}