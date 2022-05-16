package com.example.atmajayarental.data.api

import com.example.atmajayarental.data.api.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface DriverApi {

    @GET
    suspend fun getDriver(
        @Url url: String,
        @Header("Authorization") token: String
    ): DriverResponse
}