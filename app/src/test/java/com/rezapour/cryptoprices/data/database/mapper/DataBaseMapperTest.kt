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
        assertThat(mapper.assetDatabaseEntityListToAssetList(listOf(SampleDataFactory.getDataBaseEntity()))).isEqualTo(
            listOf(SampleDataFactory.getAssetForDataBaseTest())
        )
    }

    @Test
    fun assetListToAssetDatabaseEntityList() {
        assertThat(mapper.assetListToAssetDatabaseEntityList(listOf(SampleDataFactory.getAssetForDataBaseTest()))).isEqualTo(
            listOf(SampleDataFactory.getDataBaseEntityWithNullObjects())
        )
    }

    @Test
    fun assetDetailToAssetDatabaseEntity(){
        assertThat(mapper.assetDetailToAssetDataBaseEntity(SampleDataFactory.getAssetDetail())).isEqualTo(SampleDataFactory.getDataBaseEntity())
    }

    @Test
    fun assetDatabaseEntityToAssetDetail(){
        assertThat(mapper.assetDataBaseEntityToAssetDetail(SampleDataFactory.getDataBaseEntity())).isEqualTo(SampleDataFactory.getAssetDetail())
    }

    @Test
    fun favoriteDatabaseEntityToAsset(){
        assertThat(mapper.favoriteDatabaseEntityListToAssetList(listOf(SampleDataFactory.getFavoriteDatabaseEntity()))).isEqualTo(
            listOf(SampleDataFactory.getAssetForDataBaseTest())
        )
    }
    @Test
    fun assetToFavoriteDatabaseEntity(){
        assertThat(mapper.assetToFavoriteDatabaseEntity(SampleDataFactory.getAssetForDataBaseTest())).isEqualTo(SampleDataFactory.getFavoriteDatabaseEntity())
    }
}