package com.rezapour.cryptoprices.data.network.impl

import com.rezapour.cryptoprices.data.exception.DataProviderException
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.exception.ExceptionMapper
import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.data.network.model.ExchangeRateNetworkEntity
import com.rezapour.cryptoprices.data.network.retrofit.Api
import retrofit2.Response

/*TODO: It is better that each layer has their own models.
       Like this layer. base on the for more separation between the layers an more flexibility.

   Note: Since this api do not support pagination and in single call fetch 17000 records I decided to directly call Image Url api and do not use the
          api call in documentation. Because it took a lot of time to map this data to each other.
          For improvement we can call the Icon url once and get the base url of the icons and use that instead of hard coding it in the app in case of
          change.

 */
class ApiProviderImpl(private val api: Api) : ApiProvider {
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