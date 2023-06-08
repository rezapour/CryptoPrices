package com.rezapour.cryptoprices.data.network.exception

import com.rezapour.cryptoprices.R

object ExceptionMapper {
    fun toApiCallErrorMessage(responseCode: Int) =
        when (responseCode) {
            in 400..499 -> R.string.error_access_denied
            in 500..599 -> R.string.error_server_error
            else -> R.string.error_internet_connection
        }

    fun toInternetConnectionError() = R.string.error_internet_connection

    fun toServerError() = R.string.error_server_error
}