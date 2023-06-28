package com.rezapour.cryptoprices.di.repository

import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.data.database.room.dao.FavoriteDao
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.data.repository.FavoriteRepository
import com.rezapour.cryptoprices.data.repository.impl.AssetRepositoryImpl
import com.rezapour.cryptoprices.data.repository.impl.FavoriteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAssetRepository(
        apiProvider: ApiProvider,
        assetDao: AssetDao,
        networkDataMapper: NetworkDataMapper,
        databaseMapper: DataBaseMapper,
    ): AssetRepository =
        AssetRepositoryImpl(
            apiProvider,
            assetDao,
            networkDataMapper,
            databaseMapper
        )

    @Singleton
    @Provides
    fun provideFavoriteRepository(
        favoriteDao: FavoriteDao,
        databaseMapper: DataBaseMapper
    ): FavoriteRepository = FavoriteRepositoryImpl(favoriteDao, databaseMapper)
}