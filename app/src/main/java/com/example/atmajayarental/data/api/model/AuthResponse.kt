package com.example.atmajayarental.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(

    @Json(name = "message")
    val message: String ? = null,

    @Json(name = "user")
    val user: User ? = null,

    @Json(name = "user_detail")
    val userDetail: Any ? = null,

    @Json(name = "token_type")
    val token_type: String ? = null,

    @Json(name = "access_token")
    val access_token: String ? = null,
)
