package com.example.atmajayarental.data.api

import com.example.atmajayarental.data.api.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface TransaksiApi {

    @GET
    suspend fun getTransaksi(
        @Url url: String,
        @Header("Authorization") token: String
    ): TransaksiResponse

    @PUT
    suspend fun updateTransaksi(
        @Url url: String,
        @Body transaksi: Transaksi,
        @Header("Authorization") token: String
    ): TransaksiResponse
}