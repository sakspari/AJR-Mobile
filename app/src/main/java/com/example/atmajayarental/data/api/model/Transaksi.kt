package com.example.atmajayarental.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transaksi(
    @Json(name = "bukti_pembayaran")
    val buktiPembayaran: String?,
    @Json(name = "foto_customer")
    val fotoCustomer: String?,
    @Json(name = "foto_driver")
    val fotoDriver: String?,
    @Json(name = "foto_mobil")
    val fotoMobil: String?,
    @Json(name = "grand_total")
    val grandTotal: Int?,
    @Json(name = "harga_satuan_driver")
    val hargaSatuanDriver: Int?,
    @Json(name = "harga_satuan_mobil")
    val hargaSatuanMobil: Int?,
    @Json(name = "id_customer")
    val idCustomer: String,
    @Json(name = "id_driver")
    val idDriver: String?,
    @Json(name = "id_mobil")
    val idMobil: String,
    @Json(name = "id_pegawai")
    val idPegawai: String?,
    @Json(name = "kode_promo")
    val kodePromo: String?,
    @Json(name = "id_transaksi")
    val idTransaksi: String,
    @Json(name = "idcard_customer")
    val idcardCustomer: String?,
    @Json(name = "metode_pembayaran")
    val metodePembayaran: Int?,
    @Json(name = "nama_customer")
    val namaCustomer: String?,
    @Json(name = "nama_driver")
    val namaDriver: String?,
    @Json(name = "nama_mobil")
    val namaMobil: String?,
    @Json(name = "nama_pegawai")
    val namaPegawai: String?,
    @Json(name = "rating_driver")
    val ratingDriver: Float?,
    @Json(name = "review_driver")
    val reviewDriver: String?,
    @Json(name = "sim_customer")
    val simCustomer: String?,
    @Json(name = "status_transaksi")
    val statusTransaksi: String,
    @Json(name = "subtotal_driver")
    val subtotalDriver: Int?,
    @Json(name = "subtotal_mobil")
    val subtotalMobil: Int?,
    @Json(name = "total_denda")
    val totalDenda: Int?,
    @Json(name = "total_diskon")
    val totalDiskon: Int?,
    @Json(name = "waktu_mulai")
    val waktuMulai: String,
    @Json(name = "waktu_pengembalian")
    val waktuPengembalian: String?,
    @Json(name = "waktu_selesai")
    val waktuSelesai: String,
    @Json(name = "waktu_transaksi")
    val waktuTransaksi: String
)