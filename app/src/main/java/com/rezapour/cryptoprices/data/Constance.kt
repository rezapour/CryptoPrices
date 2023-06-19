package com.rezapour.cryptoprices.data

import com.rezapour.cryptoprices.BuildConfig

//Note: For Apikey you can put it in the gradle properties with the key API_KEY.
object Constance {
    const val BASE_URL = "https://rest.coinapi.io/v1/"
    const val API_KEY = BuildConfig.API_KEY
    const val TIME_OUT = 30000L
    const val DATA_BASE_NAME = "AppDataBase"
    fun getImageUrl(imageId: String?) =
        "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_64/$imageId.png"

    const val PREFERENCE_NAME="Setting"
}