package com.rezapour.cryptoprices.di.network

import com.rezapour.cryptoprices.data.Constance
import com.rezapour.cryptoprices.data.network.retrofit.Api
import com.rezapour.cryptoprices.data.network.retrofit.client.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(retrofit: Retrofit): Retrofit {
        return RetrofitClient().retrofitProvider(Constance.BASE_URL, Constance.TIME_OUT)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}