package com.example.atmajayarental.data.api

import com.example.atmajayarental.data.api.model.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {

    @FormUrlEncoded
    @POST(UrlDataSource.LOGIN)
//    @Headers("Accept:application/json","Content-Type:application/json")
    suspend fun loginRequest(
        @Field("email") email: String,
        @Field("password") password: String
    ) : AuthResponse
}