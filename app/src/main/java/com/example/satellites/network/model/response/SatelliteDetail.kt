package com.example.satellites.network.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SatelliteDetail(
    val id: Int,
    @Json(name = "cost_per_launch") val costPerLaunch: Int,
    @Json(name = "first_flight") val firstFlightDate: String,
    val height: Int,
    val mass: Int
)
