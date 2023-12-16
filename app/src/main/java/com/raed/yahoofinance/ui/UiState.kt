package com.raed.yahoofinance.ui

import com.raed.yahoofinance.data.model.Quote

/**
 * Created by Raed Saeed on 12/16/2023
 */
data class UiState(
    val isLoading : Boolean,
    val error : String? = null,
    val dataSet : List<Quote>? = null
)