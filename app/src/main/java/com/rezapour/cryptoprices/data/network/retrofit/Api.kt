package com.rezapour.cryptoprices.data.network.retrofit

import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.data.network.model.ExchangeRateNetworkEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("assets")
    suspend fun getAssets(): Response<List<AssetNetworkEntity>>

    @GET("assets/{assetId}")
    suspend fun getAssetDetail(@Path("assetId") assetId: String): Response<List<AssetNetworkEntity>>

    @GET("exchangerate/{assetIdBase}/{assetIdQuote}")
    suspend fun getExchangeRate(
        @Path("assetIdBase") assetIdBase: String,
        @Path("assetIdQuote") assetIdQuote: String
    ): Response<ExchangeRateNetworkEntity>
}