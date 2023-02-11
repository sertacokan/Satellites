package com.example.satellites.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Satellite(
    val id: Int,
    @Json(name = "active") val isActive: Boolean,
    val name: String
)
