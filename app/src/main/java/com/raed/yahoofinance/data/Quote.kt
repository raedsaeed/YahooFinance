package com.raed.yahoofinance.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Raed Saeed on 12/16/2023
 */

@Parcelize
data class Quote(
    var exchange: String? = null,
    var shortname: String? = null,
    var quoteType: String? = null,
    var symbol: String? = null,
    var index: String? = null,
    var score: Int = 0,
    var typeDisp: String? = null,
    var longname: String? = null,
    var exchDisp: String? = null,
    var isYahooFinance: Boolean? = null
) : Parcelable