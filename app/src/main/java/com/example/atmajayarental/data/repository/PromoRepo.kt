package com.example.atmajayarental.data.repository

import android.util.JsonToken
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.atmajayarental.data.api.AuthApi
import com.example.atmajayarental.data.api.PromoApi
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

class PromoRepo @Inject constructor(
    private val promoApi: PromoApi,
    private val userPreferences: UserPreferencesImpl
) {

    suspend fun getAllPromos(
        token: String
    ): PromoResponse {
        return promoApi.getPromos(token = token)
    }

    suspend fun getPromos(): PromoResponse {
        var token by mutableStateOf("")
//        userPreferences.getToken().collect {
//            token = it
//        }
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                userPreferences.getToken().collect {
//                return@collect withContext(Dispatchers.IO) {
//                    promoApi.getPromos(it)
//                }
                    token = it
                    Log.i("TOKEN_IT", it)
                    Log.i("PROMOS_IT", promoApi.getPromos(it).toString())

                }
            }

//            return promoApi.getPromos(token)
        }

        Log.i("TOKEN", token)
        return promoApi.getPromos(token)
    }

//        userPreferences.getToken().collect {
//            withContext(Dispatchers.IO) {
//                promoApi.getPromos(it)
//            }
//        }
//
//    }
}