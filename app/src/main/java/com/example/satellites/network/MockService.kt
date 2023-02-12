package com.example.satellites.network

import com.example.satellites.network.adapter.NetworkState
import com.example.satellites.network.model.response.Satellite
import com.example.satellites.network.model.response.SatelliteDetail
import com.example.satellites.network.model.response.SatellitePositionListItem
import retrofit2.http.GET
import retrofit2.http.Path

interface MockService {

    @GET("list")
    suspend fun fetchSatellites(): NetworkState<List<Satellite>?>

    @GET("positions/{id}")
    suspend fun fetchPositions(@Path("id") id: Int): NetworkState<SatellitePositionListItem?>

    @GET("detail/{id}")
    suspend fun fetchDetail(@Path("id") id: Int): NetworkState<SatelliteDetail?>

}