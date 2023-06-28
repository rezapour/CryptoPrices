package com.rezapour.cryptoprices.view.viewmodels

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rezapour.cryptoprices.util.UiState
import com.rezapour.cryptoprices.data.SampleDataFactory
import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.AssetDao
import com.rezapour.cryptoprices.data.database.room.entities.AssetDatabaseEntity
import com.rezapour.cryptoprices.data.network.util.MainCoroutineRule
import com.rezapour.cryptoprices.data.prefrence.SortState
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.data.repository.FavoriteRepository
import com.rezapour.cryptoprices.util.DataState
import com.rezapour.cryptoprices.view.assetlist.AssetListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AssetListViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    lateinit var viewModel: AssetListViewModel
    lateinit var favoriteRepository: FavoriteRepository
    lateinit var assetRepository: AssetRepository
    lateinit var sortState: SortState
    lateinit var dao: AssetDao

    @Before
    fun before() {
        favoriteRepository = mock()
        assetRepository = mock()
        sortState = mock()
        dao = mock()

        viewModel = AssetListViewModel(
            assetRepository,
            favoriteRepository,
            sortState,
        )
    }

    @Test
    fun LoadDataTestSucess()= runTest{
        whenever(assetRepository.getAssets()).thenReturn(DataState.FreshResult(listOf(SampleDataFactory.getAsset())))
        whenever(favoriteRepository.getFavoriteId()).thenReturn(setOf("BTC"))
        viewModel.loadData()
        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(UiState.Success::class.java)
        }
    }

    @Test
    fun LoadDataTestError()= runTest{
        whenever(assetRepository.getAssets()).thenReturn(DataState.CacheResult(listOf(SampleDataFactory.getAsset())))
        whenever(favoriteRepository.getFavoriteId()).thenReturn(setOf("BTC"))
        viewModel.loadData()
        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(UiState.Error::class.java)
        }
    }


    @Test
    fun searchAssetSuccess() = runTest {
        whenever(assetRepository.searchAsset(any())).thenReturn(listOf(SampleDataFactory.getAssetForDataBaseTest()))
        whenever(favoriteRepository.getFavoriteId()).thenReturn(setOf("BTC"))
        viewModel.search("")
        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(UiState.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(UiState.Success::class.java)
        }
    }

    @Test
    fun getFavoriteSuccess() = runTest {
        whenever(favoriteRepository.getFavorite()).thenReturn(listOf(SampleDataFactory.getAssetForDataBaseTest()))

        viewModel.getFavorite()
        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(UiState.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(UiState.Success::class.java)
        }
    }
}