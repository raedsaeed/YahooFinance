package com.raed.yahoofinance.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.raed.yahoofinance.data.api.NetworkViewState
import com.raed.yahoofinance.data.api.RemoteExceptions
import com.raed.yahoofinance.data.model.Quote
import com.raed.yahoofinance.data.repo.GetSummaryRepoImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by Raed Saeed on 12/17/2023
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class GetSummaryUseCaseTest {

    @Mock
    private lateinit var repoImpl: GetSummaryRepoImpl

    private lateinit var useCase: GetSummaryUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = GetSummaryUseCase(repoImpl)
    }

    @Test
    fun `invoke getSummaryUseCase return loading state`() = runTest {
        val response = useCase.invoke()
        response.onStart {
            assertThat(response).isInstanceOf(NetworkViewState.Loading::class.java)
        }
    }

    @Test
    fun `invoke getSummaryUseCase return success`() = runTest {
        Mockito.`when`(repoImpl.getSummary()).then { arrayListOf(Quote()) }
        val response = useCase.invoke()
        response.onCompletion {
            assertThat(response).isInstanceOf(NetworkViewState.Success::class.java)
            verify(repoImpl, times(1)).getSummary()
            response.collect {
                val viewState = it as NetworkViewState.Success<*>
                assertThat(viewState.data).isInstanceOf(List::class.java)
                assertThat(viewState.data).isNotNull()
            }
        }
    }

    @Test
    fun `invoke getSummaryUseCase return error after throw UnauthorizedException`() = runTest {
        Mockito.doAnswer { throw RemoteExceptions.UnauthorizedException() }
            .`when`(repoImpl).getSummary()

        val response = useCase.invoke()
        response.onCompletion {
            assertThat(response).isInstanceOf(NetworkViewState.Error::class.java)
            verify(repoImpl, times(1)).getSummary()
            response.collect {
                val viewState = it as NetworkViewState.Error
                assertThat(viewState.error).isInstanceOf(RemoteExceptions.UnauthorizedException::class.java)
            }
        }
    }

    @Test
    fun `invoke getSummaryUseCase return error after throw NotFoundException`() = runTest {
        Mockito.doAnswer { throw RemoteExceptions.NotFoundException() }
            .`when`(repoImpl).getSummary()

        val response = useCase.invoke()
        response.onCompletion {
            assertThat(response).isInstanceOf(NetworkViewState.Error::class.java)
            verify(repoImpl, times(1)).getSummary()
            response.collect {
                val viewState = it as NetworkViewState.Error
                assertThat(viewState.error).isInstanceOf(RemoteExceptions.NotFoundException::class.java)
            }
        }
    }

    @Test
    fun `invoke getSummaryUseCase return error after throw ServerErrorException`() = runTest {
        Mockito.doAnswer { throw RemoteExceptions.ServerErrorException() }
            .`when`(repoImpl).getSummary()

        val response = useCase.invoke()
        response.onCompletion {
            assertThat(response).isInstanceOf(NetworkViewState.Error::class.java)
            verify(repoImpl, times(1)).getSummary()
            response.collect {
                val viewState = it as NetworkViewState.Error
                assertThat(viewState.error).isInstanceOf(RemoteExceptions.ServerErrorException::class.java)
            }
        }
    }
}