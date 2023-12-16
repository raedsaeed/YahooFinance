package com.raed.yahoofinance.domain.repo

import com.raed.yahoofinance.data.model.Quote

/**
 * Created by Raed Saeed on 12/16/2023
 */
interface ISummaryRepo {
    suspend fun getSummary() : List<Quote>
}