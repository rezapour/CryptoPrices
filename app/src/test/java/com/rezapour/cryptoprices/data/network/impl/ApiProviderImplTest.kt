package com.rezapour.cryptoprices.data.network.impl

import com.google.common.io.Resources
import com.google.common.truth.Truth.assertThat
import com.rezapour.cryptoprices.data.SampleDataFactory
import com.rezapour.cryptoprices.data.exception.DataProviderException
import com.rezapour.cryptoprices.data.network.ApiProvider
import com.rezapour.cryptoprices.data.network.mapper.NetworkDataMapper
import com.rezapour.cryptoprices.data.network.retrofit.Api
import com.rezapour.cryptoprices.data.network.retrofit.client.RetrofitClient
import com.rezapour.cryptoprices.data.network.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import com.rezapour.cryptoprices.R

@ExperimentalCoroutinesApi
class ApiProviderImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    lateinit var mockWebServer: MockWebServer
    lateinit var apiProvider: ApiProvider

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        val api =
            RetrofitClient().retrofitProvider(mockWebServer.url("/"), 1000)
                .create(Api::class.java)
        apiProvider = ApiProviderImpl(
            api,
            NetworkDataMapper()
        )
    }

    @After
    fun destroy() {
        mockWebServer.shutdown()
    }

    private fun responseMapper(fileName: String): String {
        val inputStreamUser: InputStream =
            File(Resources.getResource(fileName).toURI()).inputStream()
        return inputStreamUser.bufferedReader().use { it.readText() }
    }

    @Test
    fun `getAssets response list of Assets when run successfully`() {
        val responseTest = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(responseMapper("AssetList.json"))

        mockWebServer.enqueue(responseTest)

        runTest {
            val response = apiProvider.getAssets()
            assertThat(response).isEqualTo(SampleDataFactory.getAssets())
        }
    }

    @Test
    fun `getAssets throws internet connection exception when api call is failed`() {
        val responseTest = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)

        mockWebServer.enqueue(responseTest)

        val messageId = Assert.assertThrows(DataProviderException::class.java) {
            runTest {
                apiProvider.getAssets()
            }
        }.messageId
        assertEquals(R.string.error_internet_connection, messageId)
    }

    @Test
    fun `getAssets throws access denied when response code is 400 range`() {
        val responseTest = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)

        mockWebServer.enqueue(responseTest)


        val messageId = Assert.assertThrows(DataProviderException::class.java) {
            runTest {
                apiProvider.getAssets()
            }
        }.messageId
        assertEquals(R.string.error_access_denied, messageId)
    }

    @Test
    fun `getAssets throws server error when response code is 500 range`() {
        val responseTest = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)

        mockWebServer.enqueue(responseTest)

        val messageId = Assert.assertThrows(DataProviderException::class.java) {
            runTest {
                apiProvider.getAssets()
            }
        }.messageId
        assertEquals(R.string.error_server_error, messageId)
    }

    @Test
    fun `getCustomers throws internet connection problem when response code is unknown range`() {
        val responseTest = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_MOVED_PERM)

        mockWebServer.enqueue(responseTest)

        val messageId = Assert.assertThrows(DataProviderException::class.java) {
            runTest {
                apiProvider.getAssets()
            }
        }.messageId
        assertEquals(R.string.error_internet_connection, messageId)
    }

}