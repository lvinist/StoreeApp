package com.dicoding.storeeapp.data.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Login(
    @Json(name = "email")
    val email: String? = "",
    @Json(name = "password")
    val password: String? = ""
)
