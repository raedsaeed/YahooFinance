package com.raed.yahoofinance.data.repo

import android.util.Log
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
        Log.e("TAG", "getSummary: repo imple ", )
        return summaryDataSource.getSummary()?.marketSummaryAndSparkResponse?.result ?: emptyList()
    }
}