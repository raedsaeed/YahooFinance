package com.raed.yahoofinance.data.datasource

import com.google.common.truth.Truth.assertThat
import com.raed.yahoofinance.data.api.ApiService
import com.raed.yahoofinance.data.api.RemoteExceptions
import com.raed.yahoofinance.data.model.YahooFinanceResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/**
 * Created by Raed Saeed on 12/17/2023
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SummaryDataSourceTest {
    @Mock
    private lateinit var apiService: ApiService
    private lateinit var dataSource: SummaryDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dataSource = SummaryDataSource(apiService)
    }

    @Test
    fun `given getSummary return Success`() = runTest {

        Mockito.`when`(apiService.getSummary(anyString())).thenReturn(
            Response.success(
                YahooFinanceResponse()
            )
        )

        val response = dataSource.getSummary()

        Mockito.verify(apiService).getSummary(anyString())
        assertThat(response).isNotNull()
        assertThat(response).isInstanceOf(YahooFinanceResponse::class.java)
    }

    @Test(expected = RemoteExceptions.ServerErrorException::class)
    fun `given getSummary throw ServerException`() = runTest {
        Mockito.`when`(apiService.getSummary(anyString()))
            .thenReturn(
                Response.error(500, "some error".toResponseBody(null))
            )

        dataSource.getSummary()
    }

    @Test(expected = RemoteExceptions.UnauthorizedException::class)
    fun `given getSummary throws UnauthorizedException`() = runTest {
        Mockito.`when`(apiService.getSummary(anyString()))
            .thenReturn(
                Response.error(401, "some error".toResponseBody(null))
            )

        dataSource.getSummary()
    }

    @Test(expected = RemoteExceptions.NotFoundException::class)
    fun `given getSummary throws NotFoundException`() = runTest {
        Mockito.`when`(apiService.getSummary(anyString()))
            .thenReturn(
                Response.error(403, "some error".toResponseBody(null))
            )

        dataSource.getSummary()
    }
}