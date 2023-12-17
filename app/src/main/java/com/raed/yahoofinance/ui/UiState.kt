package com.raed.yahoofinance.ui

import androidx.annotation.StringRes
import com.raed.yahoofinance.data.model.Quote

/**
 * Created by Raed Saeed on 12/16/2023
 */
data class UiState(
    val isLoading: Boolean,
    @StringRes val error: Int? = null,
    val dataSet: List<Quote>? = null,
    val quotes: List<Quote>? = null,
    val filteredString: CharSequence? = null,
)