package com.example.satellites.database.detail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "satellite_detail")
data class SatelliteDetailEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "cost_per_launch")
    val costPerLaunch: Int,
    @ColumnInfo(name = "first_flight")
    val firstFlightDate: String,
    @ColumnInfo("height")
    val height: Int,
    @ColumnInfo("mass")
    val mass: Int
)
