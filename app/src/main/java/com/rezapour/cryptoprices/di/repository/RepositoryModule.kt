package com.rezapour.cryptoprices.di.repository

import com.rezapour.cryptoprices.data.database.DataBaseProvider
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.data.repository.impl.AssetRepositoryImpl
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
        databaseProvider: DataBaseProvider
    ): AssetRepository = AssetRepositoryImpl(apiProvider, databaseProvider)
}