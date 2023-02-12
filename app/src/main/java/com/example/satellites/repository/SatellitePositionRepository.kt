package com.example.satellites.repository

import com.example.satellites.network.MockService
import com.example.satellites.network.adapter.SuccessState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class SatellitePositionRepository(private val mockService: MockService) {

    fun fetchSatellitePositions(id: Int) = flow {
        val positionNetworkState = mockService.fetchPositions(id)
        if (positionNetworkState is SuccessState) {
            val positions = positionNetworkState.data?.positions ?: throw IllegalStateException()
            var currentIndex = 0
            while (true) {
                val position = positions[currentIndex]
                emit(position.posX to position.posY)
                delay(POSITION_INDEX_INTERVAL_MS)
                currentIndex = if (currentIndex == positions.lastIndex) 0 else currentIndex + 1
            }
        } else {
            emit(0f to 0f)
        }
    }

    companion object {
        private const val POSITION_INDEX_INTERVAL_MS = 3_000L
    }
}