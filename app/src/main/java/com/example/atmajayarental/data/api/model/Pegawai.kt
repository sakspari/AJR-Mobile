package com.example.atmajayarental.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pegawai(
    @Json(name = "address")
    val address: String,
    @Json(name = "birthdate")
    val birthdate: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "picture")
    val picture: String,
    @Json(name = "role_id")
    val roleId: String,
    @Json(name = "role_name")
    val roleName: String,
    @Json(name = "updated_at")
    val updatedAt: String
)