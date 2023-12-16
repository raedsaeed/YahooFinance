package com.raed.yahoofinance.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Raed Saeed on 12/16/2023
 */

@Parcelize
data class RegularMarketPreviousClose(
    var raw: String? = null,
    var fmt: String? = null
) : Parcelable
