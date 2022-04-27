package com.example.atmajayarental.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "email_verified_at")
    val emailVerifiedAt: String ? = null,
    @Json(name = "id")
    val id: Int,
    @Json(name = "level")
    val level: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "updated_at")
    val updatedAt: String
)