package com.rezapour.cryptoprices.data.network.mapper

import com.google.common.truth.Truth.assertThat
import com.rezapour.cryptoprices.data.SampleDataFactory
import org.junit.Before
import org.junit.Test

class NetworkDataMapperTest {

    lateinit var mapper: NetworkDataMapper

    @Before
    fun setUp() {
        mapper = NetworkDataMapper()
    }

    @Test
    fun assetNetworkEntityListToAssetList() {
        assertThat(mapper.assetNetworkEntityListToAssetList(SampleDataFactory.getAssetsNetworkEntity())).isEqualTo(
            SampleDataFactory.getAssets()
        )
    }
}