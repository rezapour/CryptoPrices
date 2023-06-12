package com.rezapour.cryptoprices.data.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.database.room.entities.AssetFavoriteDataBaseEntity

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

    @Insert
    suspend fun insertFavorite(asset: AssetFavoriteDataBaseEntity)

    @Query("DELETE FROM favorite WHERE  asset_Id= :assetId")
    suspend fun deleteFavorite(assetId: String)

    @Query("SELECT * FROM favorite")
    suspend fun getFavorite(): List<AssetFavoriteDataBaseEntity>
}