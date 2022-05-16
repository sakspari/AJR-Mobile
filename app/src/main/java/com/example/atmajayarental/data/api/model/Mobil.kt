package com.example.atmajayarental.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Mobil(
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "fasilitas_mobil")
    val fasilitasMobil: String?,
    @Json(name = "foto_mobil")
    val fotoMobil: String?,
    @Json(name = "harga_sewa")
    val hargaSewa: Int,
    @Json(name = "id_mitra")
    val idMitra: String?,
    @Json(name = "id_mobil")
    val idMobil: String,
    @Json(name = "jenis_aset")
    val jenisAset: Int,
    @Json(name = "jenis_bahan_bakar")
    val jenisBahanBakar: String,
    @Json(name = "jenis_transmisi")
    val jenisTransmisi: String,
    @Json(name = "kapasitas_penumpang")
    val kapasitasPenumpang: Int,
    @Json(name = "nama_mobil")
    val namaMobil: String,
    @Json(name = "no_stnk")
    val noStnk: String,
    @Json(name = "periode_mulai")
    val periodeMulai: String?,
    @Json(name = "periode_selesai")
    val periodeSelesai: String?,
    @Json(name = "plat_mobil")
    val platMobil: String,
    @Json(name = "servis_terakhir")
    val servisTerakhir: String,
    @Json(name = "tipe_mobil")
    val tipeMobil: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "volume_bagasi")
    val volumeBagasi: Int,
    @Json(name = "volume_bahan_bakar")
    val volumeBahanBakar: Int,
    @Json(name = "warna_mobil")
    val warnaMobil: String
)