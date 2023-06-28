package com.rezapour.cryptoprices.view.viewmodels

import android.provider.ContactsContract.Data
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.rezapour.cryptoprices.data.SampleDataFactory
import com.rezapour.cryptoprices.data.network.util.MainCoroutineRule
import com.rezapour.cryptoprices.data.repository.AssetRepository
import com.rezapour.cryptoprices.util.DataState
import com.rezapour.cryptoprices.util.UiState
import com.rezapour.cryptoprices.view.assetdetail.AssetDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AssetDetailViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: AssetDetailViewModel
    lateinit var assetRepository: AssetRepository

    @Before
    fun before() {
        assetRepository = mock()
        viewModel = AssetDetailViewModel(assetRepository)
    }


    @Test
    fun getAssetDetailFeresh() = runTest {
        whenever(assetRepository.getAssetDetail(any(), any())).thenReturn(DataState.FreshResult(SampleDataFactory.getAssetDetail()))

        viewModel.uiState.test {
            viewModel.loadAssetDetail("")
            assertThat(awaitItem()).isInstanceOf(UiState.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(UiState.Success::class.java)
        }
    }

    @Test
    fun getAssetDetailDataIsCached() = runTest {
        whenever(assetRepository.getAssetDetail(any(), any())).thenReturn(DataState.CacheResult(SampleDataFactory.getAssetDetail()))

        viewModel.uiState.test {
            viewModel.loadAssetDetail("")
            assertThat(awaitItem()).isInstanceOf(UiState.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(UiState.Error::class.java)
        }
    }
}