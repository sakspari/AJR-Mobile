package com.example.atmajayarental.data.api.model.laporan


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopDriver(
    @Json(name = "id_driver")
    val idDriver: String,
    @Json(name = "jumlah_transaksi")
    val jumlahTransaksi: Int,
    @Json(name = "month")
    val month: Int,
    @Json(name = "nama_driver")
    val namaDriver: String,
    @Json(name = "year")
    val year: Int
)