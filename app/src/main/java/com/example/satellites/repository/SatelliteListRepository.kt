package com.example.satellites.repository

import com.example.satellites.database.list.SatelliteDao
import com.example.satellites.database.list.SatelliteEntity
import com.example.satellites.network.MockService
import com.example.satellites.network.adapter.NetworkState
import com.example.satellites.network.model.response.Satellite
import kotlinx.coroutines.flow.Flow

class SatelliteListRepository(
    private val mockService: MockService,
    private val satelliteDao: SatelliteDao
) {

    val satelliteListFlow: Flow<List<SatelliteEntity>> = satelliteDao.getSatellites()

    fun searchSatellite(query: String): Flow<List<SatelliteEntity>?> {
        return satelliteDao.searchSatellites(query)
    }

    suspend fun fetchSatelliteList(): NetworkState<List<Satellite>?> {
        return mockService.fetchSatellites()
    }

    suspend fun insertSatellites(satellites: List<SatelliteEntity>) {
        satelliteDao.insertSatellites(satellites)
    }

    suspend fun getSatelliteCount(): Int {
        return satelliteDao.getSatelliteCount()
    }
}