package com.example.atmajayarental.data.api.model.Laporan


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PenyewaanMobil(
    @Json(name = "jumlah_peminjaman")
    val jumlahPeminjaman: Int,
    @Json(name = "month")
    val month: Int,
    @Json(name = "nama_mobil")
    val namaMobil: String,
    @Json(name = "pendapatan")
    val pendapatan: String,
    @Json(name = "tipe_mobil")
    val tipeMobil: String,
    @Json(name = "year")
    val year: Int
)