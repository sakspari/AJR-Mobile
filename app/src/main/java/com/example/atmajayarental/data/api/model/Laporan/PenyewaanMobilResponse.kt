package com.example.atmajayarental.data.api.model.Laporan

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PenyewaanMobilResponse(

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "data")
    val penyewaanMobils: List<PenyewaanMobil>? = null,
)