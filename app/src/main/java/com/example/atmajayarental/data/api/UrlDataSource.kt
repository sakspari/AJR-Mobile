package com.example.atmajayarental.data.api

object UrlDataSource {
    const val PUBLIC = "http://192.168.211.78:8000"
    const val API = "http://192.168.211.78:8000/api/"

    //wireless source
//    const val PUBLIC = "http://10.113.104.70:8000"
//    const val API = "http://10.113.104.70:8000/api/"

    const val LOGIN = "login/"
    const val PROMO = "promo/"
    const val MOBIL = "mobil/"
    const val CUSTOMER = "customer/"
    const val CUSTOMERBYEMAIL = "customer-email/"
    const val DRIVERBYEMAIL = "driver-email/"
    const val PEGAWAIBYEMAIL = "pegawai-email/"
    const val PEGAWAI = "pegawai/"
    const val DRIVER = "driver/"
    const val TRANSAKSI = "transaksi/"
    const val TRANSAKSIDRIVER = "transaksi-driver/"
    const val TRANSAKSICUSTOMER = "transaksi-customer/"
    const val RATINGDRIVER = "rating-driver/"

    //    generate laporan
    const val LAPORAN_PENYEWAAN_MOBIL = "penyewaan_mobil/"
    const val LAPORAN_DETAIL_PENDAPATAN = "detail_pendapatan/"
    const val LAPORAN_TOP5_DRIVER = "top_five_driver/"
    const val LAPORAN_TOP5_CUSTOMER = "top_five_customer/"
    const val LAPORAN_PERFORMA_DRIVER = "performa_driver/"
}