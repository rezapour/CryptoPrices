package com.rezapour.cryptoprices.data.repository.impl

import com.google.common.truth.Truth.assertThat
import com.rezapour.cryptoprices.data.SampleDataFactory
import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.FavoriteDao
import com.rezapour.cryptoprices.data.network.util.MainCoroutineRule
import com.rezapour.cryptoprices.data.repository.FavoriteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteRepositoryImplTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var dao: FavoriteDao
    lateinit var repository: FavoriteRepository

    @Before
    fun before() {
        dao = mock()
        repository = FavoriteRepositoryImpl(dao, DataBaseMapper())
    }

    @Test
    fun getFavorite() = runTest {
        whenever(dao.getFavorite()).thenReturn(listOf(SampleDataFactory.getFavoriteDatabaseEntity()))
        assertThat(repository.getFavorite()).isEqualTo(listOf(SampleDataFactory.getAssetForDataBaseTest()))
    }

    @Test
    fun getFavoriteId() = runTest {
        whenever(dao.getFavoriteAssetIds()).thenReturn(listOf("btc"))
        assertThat(repository.getFavoriteId()).isEqualTo(setOf("btc"))
    }

}