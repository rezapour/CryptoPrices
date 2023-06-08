package com.rezapour.cryptoprices.data.network.impl

import com.rezapour.cryptoprices.data.exception.DataProviderException
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.exception.ExceptionMapper
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.network.retrofit.Api
import com.rezapour.cryptoprices.model.Asset
import retrofit2.Response

class ApiProviderImpl(private val api: Api, private val mapper: NetworkDataMapper) : ApiProvider {
    override suspend fun getAssets(): List<Asset> {
        try {
            val response = api.getAssets()
            if (response.isSuccessful)
                if (response.isResponseValid())
                    return mapper.assetNetworkEntityListToAssetList(response.body()!!)
                else
                    throw DataProviderException(ExceptionMapper.toServerError())
            else
                throw DataProviderException(ExceptionMapper.toApiCallErrorMessage(response.code()))
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