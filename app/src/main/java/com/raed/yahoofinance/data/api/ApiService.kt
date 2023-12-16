package com.raed.yahoofinance.data.api

import com.raed.yahoofinance.data.model.YahooFinanceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Raed Saeed on 12/16/2023
 */
interface ApiService {
    @GET("market/v2/get-summary")
    suspend fun getSummary(@Query("region") region : String): Response<YahooFinanceResponse>
}