package com.raed.yahoofinance.data.repo

import com.google.common.truth.Truth.assertThat
import com.raed.yahoofinance.data.api.RemoteExceptions
import com.raed.yahoofinance.data.datasource.SummaryDataSource
import com.raed.yahoofinance.data.model.MarketSummary
import com.raed.yahoofinance.data.model.Quote
import com.raed.yahoofinance.data.model.YahooFinanceResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Raed Saeed on 12/17/2023
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetSummaryRepoImplTest {
    @Mock
    private lateinit var dataSource: SummaryDataSource

    private lateinit var repoImpl: GetSummaryRepoImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repoImpl = GetSummaryRepoImpl(dataSource)
    }

    @Test
    fun `given getSummary return success with empty list`() = runTest {
        Mockito.`when`(dataSource.getSummary()).then { YahooFinanceResponse() }

        val response = repoImpl.getSummary()
        assertThat(response).isEmpty()
        Mockito.verify(dataSource).getSummary()
    }

    @Test
    fun `given getSummary return success with list`() = runTest {
        Mockito.`when`(dataSource.getSummary()).then {
            YahooFinanceResponse(
                MarketSummary(
                    result = arrayListOf(
                        Quote()
                    )
                )
            )
        }

        val response = repoImpl.getSummary()
        assertThat(response).isNotEmpty()
        Mockito.verify(dataSource).getSummary()
    }

    @Test(expected = RemoteExceptions.UnauthorizedException::class)
    fun `given getSummary throw UnauthorizedException`() = runTest {
        Mockito.doAnswer { throw RemoteExceptions.UnauthorizedException() }
            .`when`(dataSource).getSummary()

        repoImpl.getSummary()
    }

    @Test(expected = RemoteExceptions.NotFoundException::class)
    fun `given getSummary throw NotFoundException`() = runTest {
        Mockito.doAnswer { throw RemoteExceptions.NotFoundException() }
            .`when`(dataSource).getSummary()

        repoImpl.getSummary()
    }

    @Test(expected = RemoteExceptions.ServerErrorException::class)
    fun `given getSummary throw ServerErrorException`() = runTest {
        Mockito.doAnswer { throw RemoteExceptions.ServerErrorException() }
            .`when`(dataSource).getSummary()

        repoImpl.getSummary()
    }
}