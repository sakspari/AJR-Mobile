package com.example.atmajayarental.data.api.model.laporan


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailPendapatan(
    @Json(name = "jenis_transaksi")
    val jenisTransaksi: String,
    @Json(name = "jumlah_transaksi")
    val jumlahTransaksi: Int,
    @Json(name = "month")
    val month: Int,
    @Json(name = "nama_customer")
    val namaCustomer: String,
    @Json(name = "nama_mobil")
    val namaMobil: String,
    @Json(name = "pendapatan")
    val pendapatan: Int?,
    @Json(name = "year")
    val year: Int
)