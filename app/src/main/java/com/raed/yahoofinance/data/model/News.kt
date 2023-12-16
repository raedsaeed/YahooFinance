package com.raed.yahoofinance.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Raed Saeed on 12/16/2023
 */
@Parcelize
data class News(
    var uuid: String? = null,
    var title: String? = null,
    var publisher: String? = null,
    var link: String? = null,
    var providerPublishTime: Int? = null,
    var type: String? = null
) : Parcelable