package com.rezapour.cryptoprices.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.mediator.AssetRemoteMediator
import com.rezapour.cryptoprices.data.repository.AssetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @OptIn(ExperimentalPagingApi::class)
    @ViewModelScoped
    @Provides
    fun pagerProvider(
        assetDao: AssetDao,
        repository: AssetRepository

    ): Pager<Int, AssetDatabaseEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = AssetRemoteMediator(
                repository
            ),
            pagingSourceFactory = {
                assetDao.getAllAssetsTest()
            }
        )
    }
}