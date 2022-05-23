package com.example.atmajayarental.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RerataRating(
    @Json(name = "jumlah_rating")
    val jumlahRating: Int,
    @Json(name = "jumlah_transaksi")
    val jumlahTransaksi: Int?,
    @Json(name = "rerata_rating")
    val rerataRating: Float?
)