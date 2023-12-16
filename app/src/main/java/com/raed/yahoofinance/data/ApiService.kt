package com.raed.yahoofinance.data

import com.raed.yahoofinance.data.model.YahooFinanceResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Raed Saeed on 12/16/2023
 */
interface ApiService {
    @GET("market/v2/get-summary")
    suspend fun getSummary(@Query("region") region : String): YahooFinanceResponse
}