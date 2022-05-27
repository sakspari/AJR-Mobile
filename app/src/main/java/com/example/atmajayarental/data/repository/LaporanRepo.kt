package com.example.atmajayarental.data.repository

import com.example.atmajayarental.data.api.*
import com.example.atmajayarental.data.api.model.laporan.*
import javax.inject.Inject

class LaporanRepo @Inject constructor(
    private val laporanApi: LaporanApi
) {

    suspend fun getPenyewaanMobil(
        url: String,
        token: String
    ): PenyewaanMobilResponse {
        return laporanApi.getPenyewaanMobil(url = url, token = token)
    }

    suspend fun getDetailPendapatan(
        url: String,
        token: String
    ): DetailPendapatanResponse {
        return laporanApi.getDetailPendapatan(url = url, token = token)
    }

    suspend fun getTopDriver(
        url: String,
        token: String
    ): TopDriverResponse {
        return laporanApi.getTopDriver(url = url, token = token)
    }

    suspend fun getTopCustomer(
        url: String,
        token: String
    ): TopCustomerResponse {
        return laporanApi.getTopCustomer(url = url, token = token)
    }

    suspend fun getPerformaDriver(
        url: String,
        token: String
    ): PerformaDriverResponse {
        return laporanApi.getPerformaDriver(url = url, token = token)
    }

}