package com.example.satellites.util

fun Float?.orZero() : Float{
    return this ?: 0f
}