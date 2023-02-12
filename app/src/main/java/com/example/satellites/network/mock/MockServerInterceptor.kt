package com.example.satellites.network.mock

import android.content.Context
import com.example.satellites.network.mock.MockServerRequest.*
import com.example.satellites.network.model.response.SatelliteDetail
import com.example.satellites.network.model.response.SatellitePosition
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import okhttp3.*

class MockServerInterceptor(
    private val context: Context,
    private val moshi: Moshi
) : Interceptor {

    @OptIn(ExperimentalStdlibApi::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val assets = context.assets
        val urlPaths = request.url().encodedPath().drop(1).split("/")
        val mockServerRequest = MockServerRequest.getServerRequest(urlPaths.first())
        val jsonString = assets.open(mockServerRequest.responseJsonFile).use { inputStream ->
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            String(buffer)
        }
        val response = when (mockServerRequest) {
            DETAIL -> {
                val type = Types.newParameterizedType(List::class.java, SatelliteDetail::class.java)
                val listJsonAdapter = moshi.adapter<List<SatelliteDetail>>(type)
                val satelliteDetailList = listJsonAdapter.fromJson(jsonString)
                val satelliteDetail = satelliteDetailList?.find { satelliteDetail ->
                    satelliteDetail.id == urlPaths.last().toInt()
                }
                val detailJsonAdapter = moshi.adapter<SatelliteDetail>() // OptIn
                detailJsonAdapter.toJson(satelliteDetail)
            }

            LIST -> {
                jsonString // Returns list
            }

            POSITIONS -> {
                val type =
                    Types.newParameterizedType(List::class.java, SatellitePosition::class.java)
                val positionListJsonAdapter = moshi.adapter<List<SatellitePosition>>(type)
                val satelliteDetailList = positionListJsonAdapter.fromJson(jsonString)
                val satellitePosition = satelliteDetailList?.find { satellitePosition ->
                    satellitePosition.id == urlPaths.last().toInt()
                }
                val positionJsonAdapter = moshi.adapter<SatellitePosition>() // OptIn
                positionJsonAdapter.toJson(satellitePosition)
            }
        }
        return chain.proceed(request)
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(response)
            .body(ResponseBody.create(MediaType.parse("application/json"), response.toByteArray()))
            .addHeader("content-type", "application/json")
            .build()
    }
}

private enum class MockServerRequest(
    private val encodedPath: String,
    val responseJsonFile: String
) {
    DETAIL("detail", "satellite-detail.json"),
    LIST("list", "satellites.json"),
    POSITIONS("positions", "positions.json");

    companion object {
        fun getServerRequest(encodedPath: String): MockServerRequest {
            return values().first { mockServerRequest -> mockServerRequest.encodedPath == encodedPath }
        }
    }
}