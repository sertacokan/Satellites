package com.example.satellites.util

fun Int?.orZero() : Int {
    return this ?: 0
}