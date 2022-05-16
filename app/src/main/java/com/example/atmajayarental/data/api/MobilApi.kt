package com.example.atmajayarental.data.api

import com.example.atmajayarental.data.api.model.MobilResponse
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.data.api.model.PromoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header

interface MobilApi {

    @GET(UrlDataSource.MOBIL)
    suspend fun getMobils(@Header("Authorization") token: String): MobilResponse
}