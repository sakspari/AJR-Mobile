package com.example.atmajayarental.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PromoResponse (

    @Json(name = "message")
    val message: String ? = null,

    @Json(name = "data")
    val promos: List<Promo> ? = null,
)