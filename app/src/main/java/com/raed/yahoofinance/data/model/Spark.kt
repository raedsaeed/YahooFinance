package com.raed.yahoofinance.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Raed Saeed on 12/16/2023
 */
@Parcelize
data class Spark(
    var dataGranularity: String? = null,
    var timestamp: ArrayList<String> = arrayListOf(),
    var symbol: String? = null,
    var end: String? = null,
    var previousClose: String? = null,
    var chartPreviousClose: String? = null,
    var start: String? = null
) : Parcelable