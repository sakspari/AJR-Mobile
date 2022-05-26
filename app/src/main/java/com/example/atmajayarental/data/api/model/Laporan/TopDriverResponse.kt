package com.example.atmajayarental.data.api.model.Laporan

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopDriverResponse(

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "data")
    val topDrivers: List<TopDriver>? = null,
)