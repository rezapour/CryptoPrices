package com.rezapour.cryptoprices.data.network.retrofit

import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("assets")
    suspend fun getAssets(): Response<List<AssetNetworkEntity>>
}