package com.example.satellites.network.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SatellitePositionList(
    val list: List<SatellitePositionListItem>
)

@JsonClass(generateAdapter = true)
data class SatellitePositionListItem(
    val id: Int,
    val positions: List<Position>
)

@JsonClass(generateAdapter = true)
data class Position(
    val posX: Float,
    val posY: Float
)