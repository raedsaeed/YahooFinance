package com.raed.yahoofinance.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raed.yahoofinance.R
import com.raed.yahoofinance.data.api.NetworkViewState
import com.raed.yahoofinance.data.api.RemoteExceptions
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

    @Suppress("UNCHECKED_CAST")
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
                                error = validateErrors(networkState.error)
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
                        search(_uiState.value.filteredString)
                    }
                }
            }
        }
    }

    private fun validateErrors(exception: Throwable?): Int {
        return when (exception) {
            is RemoteExceptions.UnauthorizedException -> R.string.error_label_message_unauthorized
            is RemoteExceptions.NotFoundException -> R.string.error_label_message_not_found
            is RemoteExceptions.ServerErrorException -> R.string.error_label_message_server_error
            else -> R.string.error_labe_message_unknown_error
        }
    }

    fun search(text: CharSequence?) {
        if (!text.isNullOrEmpty()) {
            val newList = _uiState.value.dataSet?.filter {
                it.shortName.toString().contains(text.toString(), true) ||
                        it.fullExchangeName.toString().contains(text.toString(), true) ||
                        it.symbol.toString().contains(text.toString(), true)
            }
            _uiState.update {
                it.copy(
                    quotes = newList,
                    filteredString = text
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    quotes = it.dataSet,
                    filteredString = null
                )
            }
        }
    }
}