package com.example.atmajayarental.data.api

import com.example.atmajayarental.data.api.model.CustomerResponse
import com.example.atmajayarental.data.api.model.MobilResponse
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.PromoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface CustomerApi {

    @GET
    suspend fun getCustomer(
        @Url url: String,
        @Header("Authorization") token: String
    ): CustomerResponse
}