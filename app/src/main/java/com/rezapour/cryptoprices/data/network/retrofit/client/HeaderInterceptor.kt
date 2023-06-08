package com.rezapour.cryptoprices.data.network.retrofit.client

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("X-CoinAPI-Key", apiKey)
                .build()
        )
    }
}