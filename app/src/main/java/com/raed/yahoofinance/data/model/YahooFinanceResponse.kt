package com.raed.yahoofinance.data.model

import com.raed.yahoofinance.data.model.News
import com.raed.yahoofinance.data.model.Quote

/**
 * Created by Raed Saeed on 12/16/2023
 */
data class YahooFinanceResponse(
    var count: Int = 0,
    var quotes: ArrayList<Quote> = arrayListOf(),
    var news: ArrayList<News> = arrayListOf(),
    var totalTime: Int = 0,
    var timeTakenForQuotes: Int = 0,
    var timeTakenForNews: Int = 0,
    var timeTakenForAlgowatchlist: Int = 0,
    var timeTakenForPredefinedScreener: Int = 0,
    var timeTakenForCrunchbase: Int = 0,
    var timeTakenForNav: Int = 0,
    var timeTakenForResearchReports: Int = 0,
    var timeTakenForScreenerField: Int = 0
)