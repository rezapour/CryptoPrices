package com.rezapour.cryptoprices.di.database

import com.rezapour.cryptoprices.data.database.DataBaseProvider
import com.rezapour.cryptoprices.data.database.impl.DataBaseProviderImpl
import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseProviderModule {
    @Singleton
    @Provides
    fun provideDatabaseProvider(dao: AssetDao, mapper: DataBaseMapper): DataBaseProvider =
        DataBaseProviderImpl(dao, mapper)
}