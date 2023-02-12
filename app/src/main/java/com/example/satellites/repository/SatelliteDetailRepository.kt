package com.example.satellites.repository

import com.example.satellites.database.detail.SatelliteDetailDao
import com.example.satellites.database.detail.SatelliteDetailEntity
import com.example.satellites.network.MockService
import com.example.satellites.network.adapter.NetworkState
import com.example.satellites.network.model.response.SatelliteDetail
import kotlinx.coroutines.flow.Flow

class SatelliteDetailRepository(
    private val mockService: MockService,
    private val satelliteDetailDao: SatelliteDetailDao
) {

    fun satelliteDetailFlow(id: Int): Flow<SatelliteDetailEntity?> {
        return satelliteDetailDao.getSatelliteDetail(id)
    }

    suspend fun fetchSatelliteDetail(id: Int): NetworkState<SatelliteDetail?> {
        return mockService.fetchDetail(id)
    }

    suspend fun insertSatelliteDetail(satelliteDetailEntity: SatelliteDetailEntity) {
        satelliteDetailDao.insertSatelliteDetail(satelliteDetailEntity)
    }

    suspend fun getSatelliteDetailCount(id: Int): Int {
        return satelliteDetailDao.getSatelliteDetailCount(id)
    }
}