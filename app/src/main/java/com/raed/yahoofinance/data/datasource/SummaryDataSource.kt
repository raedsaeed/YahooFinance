package com.raed.yahoofinance.data.datasource

import com.raed.yahoofinance.data.api.ApiService
import com.raed.yahoofinance.data.api.invokeApi
import com.raed.yahoofinance.data.model.YahooFinanceResponse
import javax.inject.Inject

/**
 * Created by Raed Saeed on 12/16/2023
 */


class SummaryDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getSummary(): YahooFinanceResponse? {
        return invokeApi { apiService.getSummary("US") }
    }
}