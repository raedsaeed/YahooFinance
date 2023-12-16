package com.raed.yahoofinance.di

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Raed Saeed on 12/16/2023
 */
class HeaderInterceptor : Interceptor {
    private companion object {
        const val RAPID_API_KEY_HEADER = "X-RapidAPI-Key"
        const val RAPID_API_KEY = "09d450b574msh466641a13b4a141p1b226djsn54a46e815d27"

    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader(RAPID_API_KEY_HEADER, RAPID_API_KEY)
        val request = builder.build()
        return chain.proceed(request)
    }
}