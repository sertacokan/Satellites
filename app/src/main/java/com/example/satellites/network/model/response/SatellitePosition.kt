package com.example.satellites.network.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SatellitePosition(
    val id: Int,
    val position: List<Position>
)

@JsonClass(generateAdapter = true)
data class Position(
    val posX: Float,
    val posy: Float
)