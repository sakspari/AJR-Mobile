package com.example.atmajayarental.data.api

import com.example.atmajayarental.data.api.model.*
import com.example.atmajayarental.data.api.model.Laporan.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface LaporanApi {

    @GET
    suspend fun getPenyewaanMobil(
        @Url url: String,
        @Header("Authorization") token: String
    ): PenyewaanMobilResponse

    @GET
    suspend fun getDetailPendapatan(
        @Url url: String,
        @Header("Authorization") token: String
    ): DetailPendapatanResponse

    @GET
    suspend fun getTopDriver(
        @Url url: String,
        @Header("Authorization") token: String
    ): TopDriverResponse

    @GET
    suspend fun getTopCustomer(
        @Url url: String,
        @Header("Authorization") token: String
    ): TopCustomerResponse

    @GET
    suspend fun getPerformaDriver(
        @Url url: String,
        @Header("Authorization") token: String
    ): PerformaDriverResponse
}