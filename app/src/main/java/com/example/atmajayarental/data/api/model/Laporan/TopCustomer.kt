package com.example.atmajayarental.data.api.model.Laporan


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopCustomer(
    @Json(name = "jumlah_transaksi")
    val jumlahTransaksi: Int,
    @Json(name = "month")
    val month: Int,
    @Json(name = "nama_customer")
    val namaCustomer: String,
    @Json(name = "year")
    val year: Int
)