package com.raed.yahoofinance.data.api

/**
 * Created by Raed Saeed on 12/16/2023
 */
sealed class RemoteExceptions : Exception() {
    class UnauthorizedException : RemoteExceptions()
    class ServerErrorException : RemoteExceptions()
    class NotFoundException : RemoteExceptions()

    class UnknownException : RemoteExceptions()

    companion object {
        enum class RemoteErrors(val code: Int) {
            UNAUTHORIZED(401),
            NOT_EXIST(403),
            SERVER_ERROR(500)
        }

        fun classifyExceptionBasedOnCode(code: Int): RemoteExceptions {
            return when (code) {
                RemoteErrors.UNAUTHORIZED.code -> UnauthorizedException()
                RemoteErrors.SERVER_ERROR.code -> ServerErrorException()
                RemoteErrors.NOT_EXIST.code -> NotFoundException()
                else -> UnknownException()
            }
        }
    }
}