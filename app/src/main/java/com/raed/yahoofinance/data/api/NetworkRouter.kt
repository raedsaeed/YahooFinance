package com.raed.yahoofinance.data.api

import retrofit2.Response


/**
 * Created by Raed Saeed on 12/16/2023
 */

suspend fun <T : Any> invokeApi(
    call: suspend () -> Response<T>,
): T? {
    val response = call()
    val body = response.body()
    val code = response.code()

    if (code == 200) return body

    throw RemoteExceptions.classifyExceptionBasedOnCode(code)
}
