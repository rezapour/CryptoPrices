package com.rezapour.cryptoprices.data.network.impl

import com.rezapour.cryptoprices.data.exception.DataProviderException
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.exception.ExceptionMapper
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.network.model.AssetNetworkEntity
import com.rezapour.cryptoprices.data.network.retrofit.Api
import com.rezapour.cryptoprices.model.AssetDetail
import retrofit2.Response

class ApiProviderImpl(private val api: Api, private val mapper: NetworkDataMapper) : ApiProvider {
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
        assetIdBase: String,
        assetIdQuote: String
    ): AssetDetail {
        try {
            val assetResponse = api.getAssetDetail(assetIdBase)
            val assetRate = api.getExchangeRate(assetIdBase, assetIdQuote)
            if (assetResponse.isSuccessful && assetRate.isSuccessful)
                if (assetResponse.isResponseValid() && assetRate.isResponseValid())
                    return mapper.assetDetailNetworkEntityToAssetDetail(
                        assetResponse.body()!![0],
                        assetRate.body()!!
                    )
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

    private fun <T> Response<T>.isResponseValid(): Boolean {
        return body() != null
    }
}