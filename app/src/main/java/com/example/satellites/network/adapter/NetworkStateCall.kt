package com.example.satellites.network.adapter

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkStateCall<T>(private val proxy: Call<T>) : Call<NetworkState<T>> {

    override fun enqueue(callback: Callback<NetworkState<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    callback.onResponse(
                        this@NetworkStateCall,
                        Response.success(Success(responseBody))
                    )
                } else {
                    callback.onResponse(
                        this@NetworkStateCall,
                        Response.success(Error)
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(
                    this@NetworkStateCall,
                    Response.success(Failure(t))
                )
            }
        })
    }

    override fun clone(): Call<NetworkState<T>> {
        return NetworkStateCall(proxy)
    }

    override fun execute(): Response<NetworkState<T>> {
        throw UnsupportedOperationException()
    }

    override fun isExecuted(): Boolean {
        return proxy.isExecuted
    }

    override fun cancel() {
        proxy.cancel()
    }

    override fun isCanceled(): Boolean {
        return proxy.isCanceled
    }

    override fun request(): Request {
        return proxy.request()
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

}