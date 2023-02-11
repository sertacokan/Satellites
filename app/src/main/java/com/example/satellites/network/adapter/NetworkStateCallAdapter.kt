package com.example.satellites.network.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkStateCallAdapter(
    private val resultType: Type
) : CallAdapter<Type, Call<NetworkState<Type>>> {
    override fun responseType(): Type {
        return resultType
    }

    override fun adapt(call: Call<Type>): Call<NetworkState<Type>> {
        return NetworkStateCall(call)
    }
}