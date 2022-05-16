package com.example.atmajayarental.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MobilResponse(

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "data")
    val mobils: List<Mobil>? = null,
)