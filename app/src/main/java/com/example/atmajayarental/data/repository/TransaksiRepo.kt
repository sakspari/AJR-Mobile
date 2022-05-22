package com.example.atmajayarental.data.repository

import android.util.JsonToken
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.atmajayarental.data.api.*
import com.example.atmajayarental.data.api.model.*
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

class TransaksiRepo @Inject constructor(
    private val transaksiApi: TransaksiApi,
    private val userPreferences: UserPreferencesImpl
) {

    suspend fun getTransaksi(
        url: String,
        token: String
    ): TransaksiResponse {
        return transaksiApi.getTransaksi(url = url, token = token)
    }

    suspend fun updateTransaksi(
        url: String,
        transaksi: Transaksi,
        token: String
    ): TransaksiResponse {
        return transaksiApi.updateTransaksi(url = url, transaksi = transaksi, token = token)
    }

}