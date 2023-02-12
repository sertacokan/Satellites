package com.example.satellites.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}