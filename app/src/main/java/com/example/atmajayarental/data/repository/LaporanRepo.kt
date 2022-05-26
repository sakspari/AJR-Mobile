package com.example.atmajayarental.data.repository

import android.util.JsonToken
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.atmajayarental.data.api.*
import com.example.atmajayarental.data.api.model.*
import com.example.atmajayarental.data.api.model.Laporan.*
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.http.hasBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Url
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