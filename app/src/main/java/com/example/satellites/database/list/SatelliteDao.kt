package com.example.satellites.database.list

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SatelliteDao {

    @Query("SELECT * FROM satellites")
    fun getSatellites(): Flow<List<SatelliteEntity>>

    @Query("SELECT * FROM satellites WHERE name LIKE '%' || :query || '%'")
    fun searchSatellites(query: String): Flow<List<SatelliteEntity>?>

    @Query("SELECT COUNT(*) FROM satellites")
    suspend fun getSatelliteCount(): Int

    @Upsert
    suspend fun insertSatellites(satellites: List<SatelliteEntity>)

}