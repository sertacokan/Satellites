package com.example.satellites.database.detail

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SatelliteDetailDao {

    @Query("SELECT * FROM satellite_detail WHERE id = :id")
    fun getSatelliteDetail(id: Int): Flow<SatelliteDetailEntity?>

    @Query("SELECT COUNT(id) FROM satellite_detail WHERE id = :id ")
    suspend fun getSatelliteDetailCount(id: Int): Int

    @Upsert
    suspend fun insertSatelliteDetail(satelliteDetailEntity: SatelliteDetailEntity)

}