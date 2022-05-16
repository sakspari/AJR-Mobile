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
import retrofit2.http.Header
import javax.inject.Inject

class PegawaiRepo @Inject constructor(
    private val pegawaiApi: PegawaiApi,
    private val userPreferences: UserPreferencesImpl
) {

    suspend fun getPegawai(
        url: String,
        token: String
    ): PegawaiResponse {
        return pegawaiApi.getPegawai(url = url, token = token)
    }

}