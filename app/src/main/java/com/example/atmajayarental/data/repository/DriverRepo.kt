package com.example.atmajayarental.data.repository

import android.util.JsonToken
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.atmajayarental.data.api.AuthApi
import com.example.atmajayarental.data.api.CustomerApi
import com.example.atmajayarental.data.api.MobilApi
import com.example.atmajayarental.data.api.PromoApi
import com.example.atmajayarental.data.api.model.CustomerResponse
import com.example.atmajayarental.data.api.model.MobilResponse
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.PromoResponse
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.Header
import javax.inject.Inject

class DriverRepo @Inject constructor(
    private val customerApi: CustomerApi,
    private val userPreferences: UserPreferencesImpl
) {

    suspend fun getCustomer(
        url: String,
        token: String
    ): CustomerResponse {
        return customerApi.getCustomer(url = url, token = token)
    }

}