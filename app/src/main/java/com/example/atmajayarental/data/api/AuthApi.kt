package com.example.atmajayarental.data.api

import com.example.atmajayarental.data.api.model.AuthResponse
import com.example.atmajayarental.data.api.model.LoginForm
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {

    @POST(UrlDataSource.LOGIN)
    @FormUrlEncoded
    suspend fun loginRequest(
        @Field("email") email: String,
        @Field("password") password: String
    ) : AuthResponse

}