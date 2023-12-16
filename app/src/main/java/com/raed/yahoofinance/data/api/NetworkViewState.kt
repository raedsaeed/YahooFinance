package com.raed.yahoofinance.data.api

/**
 * Created by Raed Saeed on 12/16/2023
 */
sealed interface NetworkViewState {
    data object Loading : NetworkViewState

    class Success<T> (val data : T? = null) : NetworkViewState where T : Any

    class Error(val error : Throwable?) : NetworkViewState
}