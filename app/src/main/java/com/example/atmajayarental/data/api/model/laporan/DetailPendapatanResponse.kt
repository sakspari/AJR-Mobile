package com.example.atmajayarental.data.api.model.laporan

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailPendapatanResponse(

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "data")
    val detailPendapatans: List<DetailPendapatan>? = null,
)