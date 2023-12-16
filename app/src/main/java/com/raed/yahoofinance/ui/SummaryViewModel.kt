package com.raed.yahoofinance.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raed.yahoofinance.data.model.NetworkViewState
import com.raed.yahoofinance.data.model.Quote
import com.raed.yahoofinance.domain.usecase.GetSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Raed Saeed on 12/16/2023
 */

@HiltViewModel
class SummaryViewModel @Inject constructor(private val getSummaryUseCase: GetSummaryUseCase) :
    ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

    fun testViewModel() {
        Log.e("TAG", "testViewModel: ", )
    }
    fun getSummary() {
        viewModelScope.launch {
            getSummaryUseCase.invoke().collect { networkState ->
                when (networkState) {
                    is NetworkViewState.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is NetworkViewState.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = networkState.error?.message
                            )
                        }
                    }

                    is NetworkViewState.Success<*> -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                dataSet = networkState.data as? List<Quote>
                            )
                        }
                    }
                }
            }
        }
    }

    fun consumeAction() {
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }
}