package com.raed.yahoofinance.ui

import com.google.common.truth.Truth.assertThat
import com.raed.yahoofinance.MainCoroutineRule
import com.raed.yahoofinance.data.api.NetworkViewState
import com.raed.yahoofinance.data.api.RemoteExceptions
import com.raed.yahoofinance.data.model.Quote
import com.raed.yahoofinance.domain.usecase.GetSummaryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
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
@RunWith(MockitoJUnitRunner::class)
class SummaryViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var useCase: GetSummaryUseCase

    private lateinit var viewModel: SummaryViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = SummaryViewModel(useCase)
    }

    @Test
    fun `call getSummary return one item`() = runTest {
        Mockito.`when`(useCase.invoke())
            .thenReturn(
                flow { emit(NetworkViewState.Success<List<Quote>>(data = arrayListOf(Quote()))) }
            )

        assertThat(viewModel.uiState.value.dataSet).isNull()
        assertThat(viewModel.uiState.value.error).isNull()
        assertThat(viewModel.uiState.value.isLoading).isTrue()

        viewModel.getSummary()
        verify(useCase, times(1)).invoke()

        assertThat(viewModel.uiState.value.dataSet).isNotEmpty()
        assertThat(viewModel.uiState.value.error).isNull()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun `call getSummary return empty list`() {
        Mockito.`when`(useCase.invoke())
            .thenReturn(
                flow { emit(NetworkViewState.Success<List<Quote>>(data = emptyList())) }
            )

        assertThat(viewModel.uiState.value.dataSet).isNull()
        assertThat(viewModel.uiState.value.error).isNull()
        assertThat(viewModel.uiState.value.isLoading).isTrue()

        viewModel.getSummary()
        verify(useCase, times(1)).invoke()

        assertThat(viewModel.uiState.value.dataSet).isEmpty()
        assertThat(viewModel.uiState.value.error).isNull()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun `call getSummary throws UnauthorizedException to return error`() {
        Mockito.`when`(useCase.invoke())
            .thenReturn(
                flow { emit(NetworkViewState.Error(error = RemoteExceptions.UnauthorizedException())) }
            )

        viewModel.getSummary()
        verify(useCase, times(1)).invoke()
        assertThat(viewModel.uiState.value.dataSet).isNull()
        assertThat(viewModel.uiState.value.error).isNotNull()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun `call getSummary throws NotFoundException to return error`() {
        Mockito.`when`(useCase.invoke())
            .thenReturn(
                flow { emit(NetworkViewState.Error(error = RemoteExceptions.NotFoundException())) }
            )

        viewModel.getSummary()
        verify(useCase, times(1)).invoke()
        assertThat(viewModel.uiState.value.dataSet).isNull()
        assertThat(viewModel.uiState.value.error).isNotNull()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun `call getSummary throws ServerErrorException to return error`() {
        Mockito.`when`(useCase.invoke())
            .thenReturn(
                flow { emit(NetworkViewState.Error(error = RemoteExceptions.ServerErrorException())) }
            )

        viewModel.getSummary()
        verify(useCase, times(1)).invoke()
        assertThat(viewModel.uiState.value.dataSet).isNull()
        assertThat(viewModel.uiState.value.error).isNotNull()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun `call search with text should return filtered list`() {
        Mockito.`when`(useCase.invoke())
            .thenReturn(
                flow {
                    emit(
                        NetworkViewState.Success<List<Quote>>(
                            data = arrayListOf(
                                Quote(symbol = "BTC"),
                                Quote(symbol = "ETH")
                            )
                        )
                    )
                }
            )

        viewModel.getSummary()
        viewModel.search("BTC")

        verify(useCase, times(1)).invoke()
        assertThat(viewModel.uiState.value.dataSet).hasSize(2)
        assertThat(viewModel.uiState.value.quotes).hasSize(1)
        assertThat(viewModel.uiState.value.filteredString).isEqualTo("BTC")
    }

    @Test
    fun `call search with empty text should return same list`() {
        Mockito.`when`(useCase.invoke())
            .thenReturn(
                flow {
                    emit(
                        NetworkViewState.Success<List<Quote>>(
                            data = arrayListOf(
                                Quote(symbol = "BTC"),
                                Quote(symbol = "ETH")
                            )
                        )
                    )
                }
            )

        viewModel.getSummary()
        viewModel.search("")

        verify(useCase, times(1)).invoke()
        assertThat(viewModel.uiState.value.dataSet).hasSize(2)
        assertThat(viewModel.uiState.value.quotes).hasSize(2)
        assertThat(viewModel.uiState.value.filteredString).isEqualTo(null)
    }
}