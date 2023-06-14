package com.rezapour.cryptoprices.data.network.retrofit

import com.rezapour.cryptoprices.data.network.model.AssetIconNetWorkEntity
import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.data.network.model.ExchangeRateNetworkEntity
import com.rezapour.cryptoprices.model.AssetDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface Api {
    @GET("assets")
    suspend fun getAssets(): Response<List<AssetNetworkEntity>>

    @GET("assets/icons/64")
    suspend fun getIcons(): Response<List<AssetIconNetWorkEntity>>

    @GET("assets/{assetId}")
    suspend fun getAssetDetail(@Path("assetId") assetId: String): Response<List<AssetNetworkEntity>>

    @GET("exchangerate/{assetIdBase}/{assetIdQuote}")
    suspend fun getExchangeRate(
        @Path("assetIdBase") assetIdBase: String,
        @Path("assetIdQuote") assetIdQuote: String
    ): Response<ExchangeRateNetworkEntity>
}