package com.rezapour.cryptoprices.data.network.impl

import com.rezapour.cryptoprices.data.exception.DataProviderException
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.exception.ExceptionMapper
import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.data.network.model.ExchangeRateNetworkEntity
import com.rezapour.cryptoprices.data.network.retrofit.Api
import retrofit2.Response

class ApiProviderImpl(private val api: Api) : ApiProvider {
    //Note:
    override suspend fun getAssets(): List<AssetNetworkEntity> {
        try {
            val assetResponse = api.getAssets()
            if (assetResponse.isSuccessful)
                if (assetResponse.isResponseValid())
                    return assetResponse.body()!!
                else
                    throw DataProviderException(ExceptionMapper.toServerError())
            else
                throw DataProviderException(ExceptionMapper.toApiCallErrorMessage(assetResponse.code()))
        } catch (e: Exception) {
            if (e is DataProviderException)
                throw e
            throw DataProviderException(ExceptionMapper.toInternetConnectionError())
        }
    }

    override suspend fun getAssetDetail(
        assetIdBase: String
    ): AssetNetworkEntity {
        try {
            val assetResponse = api.getAssetDetail(assetIdBase)
            if (assetResponse.isSuccessful)
                if (assetResponse.isResponseValid())
                    return assetResponse.body()!![0]
                else
                    throw DataProviderException(ExceptionMapper.toServerError())
            else
                throw DataProviderException(ExceptionMapper.toApiCallErrorMessage(assetResponse.code()))
        } catch (e: Exception) {
            if (e is DataProviderException)
                throw e
            throw DataProviderException(ExceptionMapper.toInternetConnectionError())
        }
    }

    override suspend fun getAssetPrice(
        assetIdBase: String,
        assetIdQuote: String
    ): ExchangeRateNetworkEntity {
        try {
            val exchangeRateResponse = api.getExchangeRate(assetIdBase, assetIdQuote)
            if (exchangeRateResponse.isSuccessful)
                if (exchangeRateResponse.isResponseValid())
                    return exchangeRateResponse.body()!!
                else
                    throw DataProviderException(ExceptionMapper.toServerError())
            else
                throw DataProviderException(
                    ExceptionMapper.toApiCallErrorMessage(
                        exchangeRateResponse.code()
                    )
                )
        } catch (e: Exception) {
            if (e is DataProviderException)
                throw e
            throw DataProviderException(ExceptionMapper.toInternetConnectionError())
        }
    }

    private fun <T> Response<T>.isResponseValid(): Boolean {
        return body() != null
    }
}