package com.rezapour.cryptoprices.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.data.database.room.dao.FavoriteDao
import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.database.room.entities.FavoriteDataBaseEntity

@Database(entities = [AssetDatabaseEntity::class, FavoriteDataBaseEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun assetDao(): AssetDao

    abstract fun favoriteDao(): FavoriteDao
}