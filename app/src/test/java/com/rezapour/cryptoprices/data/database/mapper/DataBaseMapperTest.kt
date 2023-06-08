package com.rezapour.cryptoprices.data.database.mapper


import com.google.common.truth.Truth.assertThat
import com.rezapour.cryptoprices.data.SampleDataFactory
import org.junit.Before
import org.junit.Test

class DataBaseMapperTest {

    private lateinit var mapper: DataBaseMapper

    @Before
    fun setUp() {
        mapper = DataBaseMapper()
    }

    @Test
    fun assetDatabaseEntityListToAssetList() {
        assertThat(mapper.assetDatabaseEntityListToAssetList(SampleDataFactory.getAssetDataBaseEntity())).isEqualTo(
            SampleDataFactory.getAssetsTwoItems()
        )
    }

    @Test
    fun assetListToAssetDatabaseEntityList() {
        assertThat(mapper.assetListToAssetDatabaseEntityList(SampleDataFactory.getAssetsTwoItems())).isEqualTo(
            SampleDataFactory.getAssetDataBaseEntity()
        )
    }
}