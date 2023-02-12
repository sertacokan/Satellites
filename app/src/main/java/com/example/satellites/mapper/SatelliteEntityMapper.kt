package com.example.satellites.mapper

import com.example.satellites.database.list.SatelliteEntity
import com.example.satellites.network.model.response.Satellite

class SatelliteEntityMapper : Mapper<List<Satellite>, List<SatelliteEntity>> {
    override fun map(input: List<Satellite>): List<SatelliteEntity> {
        return input.map { satellite ->
            SatelliteEntity(
                satellite.id,
                if (satellite.isActive) 1 else 0,
                satellite.name
            )
        }
    }
}