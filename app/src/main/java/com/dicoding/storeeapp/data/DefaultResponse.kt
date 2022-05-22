package com.dicoding.storeeapp.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class DefaultResponse(
    @Json(name = "error")
    val error: Boolean = false,
    @Json(name = "message")
    val message: String = ""
): Parcelable
