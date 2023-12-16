package com.raed.yahoofinance.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Raed Saeed on 12/16/2023
 */
@Parcelize
data class Quote(
    var exchangeTimezoneName: String? = null,
    var fullExchangeName: String? = null,
    var symbol: String? = null,
    var cryptoTradeable: Boolean? = null,
    var gmtOffSetMilliseconds: String? = null,
    var firstTradeDateMilliseconds: String? = null,
    var exchangeDataDelayedBy: String? = null,
    var language: String? = null,
    var regularMarketTime: RegularMarketTime? = RegularMarketTime(),
    var exchangeTimezoneShortName: String? = null,
    var quoteType: String? = null,
    var marketState: String? = null,
    var customPriceAlertConfidence: String? = null,
    var market: String? = null,
    var spark: Spark? = Spark(),
    var priceHint: Float? = null,
    var tradeable: Boolean? = null,
    var exchange: String? = null,
    var sourceInterval: Int? = null,
    var shortName: String? = null,
    var region: String? = null,
    var triggerable: Boolean? = null,
    var regularMarketPreviousClose: RegularMarketPreviousClose? = RegularMarketPreviousClose()
) : Parcelable
