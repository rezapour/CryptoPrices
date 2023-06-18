package com.rezapour.cryptoprices.data.repository.impl


import com.rezapour.cryptoprices.data.SampleDataFactory
import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.network.util.MainCoroutineRule
import com.rezapour.cryptoprices.data.repository.AssetRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import com.google.common.truth.Truth.assertThat
import com.rezapour.cryptoprices.data.exception.DataProviderException
import com.rezapour.cryptoprices.model.CacheableResult
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class AssetRepositoryImplTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var apiProvider: ApiProvider

    private lateinit var dao: AssetDao
    private lateinit var repository: AssetRepository

    @Before
    fun before() {
        apiProvider = mock()
        dao = mock()
        repository = AssetRepositoryImpl(apiProvider, dao, NetworkDataMapper(), DataBaseMapper())
    }


    @Test
    fun searchAsset() = runTest {
        whenever(dao.searchAsset(any())).thenReturn(listOf(SampleDataFactory.getDataBaseEntity()))

        assertThat(repository.searchAsset("")).isEqualTo(listOf(SampleDataFactory.getAssetForDataBaseTest()))
    }

    @Test
    fun getAssetDetailFreshData() = runTest {
        whenever(apiProvider.getAssetDetail(any())).thenReturn(SampleDataFactory.getNetworkEntity())
        whenever(
            apiProvider.getAssetPrice(
                any(),
                any()
            )
        ).thenReturn(SampleDataFactory.getExchangeRateNetworkEntity())

        assertThat(repository.getAssetDetail("", "")).isEqualTo(
            CacheableResult.initFreshenResult(
                SampleDataFactory.getAssetDetail()
            )
        )
    }

    @Test
    fun getAssetDetailCachedDataAssetDetailFailed() = runTest {
        whenever(dao.searchAsset(any())).thenReturn(listOf(SampleDataFactory.getDataBaseEntity()))
        whenever(apiProvider.getAssetDetail(any())).thenThrow(DataProviderException(1))
        assertThat(repository.getAssetDetail("")).isEqualTo(
            CacheableResult.initCachedResult(
                SampleDataFactory.getAssetDetail()
            )
        )
    }

    @Test
    fun getAssetDetailCachedDataRateFailed() = runTest {
        whenever(dao.searchAsset(any())).thenReturn(listOf(SampleDataFactory.getDataBaseEntity()))
        whenever(apiProvider.getAssetPrice(any(), any())).thenThrow(DataProviderException(1))
        assertThat(repository.getAssetDetail("")).isEqualTo(
            CacheableResult.initCachedResult(
                SampleDataFactory.getAssetDetail()
            )
        )
    }
}