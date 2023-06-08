package com.rezapour.cryptoprices.di.network

import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.impl.ApiProviderImpl
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.network.retrofit.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiProviderModule {

    @Singleton
    @Provides
    fun provideNetworkDataProvider(
        apiService: Api,
        mapper: NetworkDataMapper
    ): ApiProvider = ApiProviderImpl(apiService, mapper)
}