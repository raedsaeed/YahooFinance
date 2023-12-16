package com.raed.yahoofinance.data.repo

import com.raed.yahoofinance.data.datasource.SummaryDataSource
import com.raed.yahoofinance.data.model.Quote
import com.raed.yahoofinance.domain.repo.ISummaryRepo
import javax.inject.Inject

/**
 * Created by Raed Saeed on 12/16/2023
 */
class GetSummaryRepoImpl @Inject constructor(private val summaryDataSource: SummaryDataSource) :
    ISummaryRepo {
    override suspend fun getSummary(): List<Quote> {
        return summaryDataSource.getSummary().quotes
    }
}