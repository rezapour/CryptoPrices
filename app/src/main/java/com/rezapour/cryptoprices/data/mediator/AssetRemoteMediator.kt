package com.rezapour.cryptoprices.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.AppDataBase
import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.exception.DataProviderException
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.repository.AssetRepository

@OptIn(ExperimentalPagingApi::class)
class AssetRemoteMediator(
    private val assetRepository: AssetRepository
) :
    RemoteMediator<Int, AssetDatabaseEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AssetDatabaseEntity>
    ): MediatorResult {
        return try {
            assetRepository.getAssets()
//            val response = apiProvider.getAssets()
//            assetDb.withTransaction {
//                if (loadType == LoadType.REFRESH)
//                    assetDb.assetDao().deleteAll()
//
//                assetDb.assetDao()
//                    .insertAll(
//                        dataBaseMapper.assetListToAssetDatabaseEntityList(
//                            networkMapper.assetNetworkEntityListToAssetList(
//                                response
//                            )
//                        )
//                    )
            MediatorResult.Success(
                endOfPaginationReached = true
            )
//            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        } catch (e: DataProviderException) {
            MediatorResult.Error(e)
        }
    }
}