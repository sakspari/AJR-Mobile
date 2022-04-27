package com.example.atmajayarental.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Promo(
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "deskripsi_promo")
    val deskripsiPromo: String,
    @Json(name = "jenis_promo")
    val jenisPromo: String,
    @Json(name = "kode_promo")
    val kodePromo: String,
    @Json(name = "persen_diskon")
    val persenDiskon: Int,
    @Json(name = "status_promo")
    val statusPromo: Int,
    @Json(name = "updated_at")
    val updatedAt: String
)