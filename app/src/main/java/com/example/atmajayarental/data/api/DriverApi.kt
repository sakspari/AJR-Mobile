package com.example.atmajayarental.data.api

import com.example.atmajayarental.data.api.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface DriverApi {

    @GET
    suspend fun getDriver(
        @Url url: String,
        @Header("Authorization") token: String
    ): DriverResponse

    @PUT
    suspend fun updateDriver(
        @Url url: String,
        @Body driver: Driver,
        @Header("Authorization") token: String
    ): DriverResponse
}