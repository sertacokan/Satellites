package com.example.satellites.network.adapter

sealed interface NetworkState<out T>

data class SuccessState<T>(val data: T?) : NetworkState<T>
object ErrorState : NetworkState<Nothing>
data class FailureState(val throwable: Throwable) : NetworkState<Nothing>