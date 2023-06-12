package com.rezapour.cryptoprices.data.network.impl

import android.util.Log
import com.rezapour.cryptoprices.data.exception.DataProviderException
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.exception.ExceptionMapper
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.network.model.AssetIconNetWorkEntity
import com.rezapour.cryptoprices.data.network.retrofit.Api
import com.rezapour.cryptoprices.model.Asset
import retrofit2.Response

class ApiProviderImpl(private val api: Api, private val mapper: NetworkDataMapper) : ApiProvider {
    //TODO find a better solution
    override suspend fun getAssets(): List<Asset> {
        try {
            val assetResponse = api.getAssets()
            val assetIcons=api.getIcons()
            if (assetResponse.isSuccessful && assetIcons.isSuccessful)
                if (assetResponse.isResponseValid() && assetIcons.isResponseValid())
                    return mapper.assetNetworkEntityListToAssetList(assetResponse.body()!!,assetIcons.body()!!)
                else
                    throw DataProviderException(ExceptionMapper.toServerError())
            else
                throw DataProviderException(ExceptionMapper.toApiCallErrorMessage(assetResponse.code()))
        } catch (e: Exception) {
            Log.d("REZAAPP",e.message.toString())
            if (e is DataProviderException)
                throw e
            throw DataProviderException(ExceptionMapper.toInternetConnectionError())
        }
    }

    private fun <T> Response<T>.isResponseValid(): Boolean {
        return body() != null
    }
}