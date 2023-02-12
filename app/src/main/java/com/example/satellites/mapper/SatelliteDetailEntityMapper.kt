package com.example.satellites.mapper

import com.example.satellites.database.detail.SatelliteDetailEntity
import com.example.satellites.network.model.response.SatelliteDetail

class SatelliteDetailEntityMapper : Mapper<SatelliteDetail, SatelliteDetailEntity> {
    override fun map(input: SatelliteDetail): SatelliteDetailEntity {
        return input.run {
            SatelliteDetailEntity(id, costPerLaunch, firstFlightDate, height, mass)
        }
    }
}