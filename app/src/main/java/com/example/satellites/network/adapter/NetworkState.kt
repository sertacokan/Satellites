package com.example.satellites.network.adapter

sealed interface NetworkState<out T>

data class Success<T>(val data: T?) : NetworkState<T>
object Error : NetworkState<Nothing>
data class Failure(val throwable: Throwable) : NetworkState<Nothing>