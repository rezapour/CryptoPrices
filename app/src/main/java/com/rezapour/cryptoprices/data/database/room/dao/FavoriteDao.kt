package com.rezapour.cryptoprices.data.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rezapour.cryptoprices.data.database.room.entities.FavoriteDataBaseEntity

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavorite(asset: FavoriteDataBaseEntity)

    @Query("DELETE FROM favorite WHERE  asset_Id= :assetId")
    suspend fun deleteFavorite(assetId: String)

    @Query("SELECT * FROM favorite")
    suspend fun getFavorite(): List<FavoriteDataBaseEntity>

    @Query("SELECT asset_id FROM favorite")
    suspend fun getFavoriteAssetIds(): List<String>
}