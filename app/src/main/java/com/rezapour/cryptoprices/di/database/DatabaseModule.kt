package com.rezapour.cryptoprices.di.database

import android.content.Context
import androidx.room.Room
import com.rezapour.cryptoprices.data.Constance
import com.rezapour.cryptoprices.data.database.room.AppDataBase
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.data.database.room.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            Constance.DATA_BASE_NAME
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideAssetDao(appDataBase: AppDataBase): AssetDao {
        return appDataBase.assetDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(appDataBase: AppDataBase): FavoriteDao {
        return appDataBase.favoriteDao()
    }
}