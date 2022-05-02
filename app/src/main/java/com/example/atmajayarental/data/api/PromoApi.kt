package com.example.atmajayarental.data.api

import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.PromoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header

interface PromoApi {

    @GET(UrlDataSource.PROMO)
    suspend fun getPromos(@Header("Authorization") token: String): PromoResponse
}