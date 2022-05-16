package com.example.atmajayarental.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Driver(
    @Json(name = "address")
    val address: String,
    @Json(name = "birthdate")
    val birthdate: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "file_bebas_napza")
    val fileBebasNapza: String,
    @Json(name = "file_sim")
    val fileSim: String,
    @Json(name = "file_sk_jasmani")
    val fileSkJasmani: String,
    @Json(name = "file_sk_jiwa")
    val fileSkJiwa: String,
    @Json(name = "file_skck")
    val fileSkck: String,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "language")
    val language: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "picture")
    val picture: String,
    @Json(name = "price")
    val price: Int,
    @Json(name = "status")
    val status: Int,
    @Json(name = "updated_at")
    val updatedAt: String
)