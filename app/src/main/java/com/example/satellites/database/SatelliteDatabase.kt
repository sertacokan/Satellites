package com.example.satellites.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.satellites.database.detail.SatelliteDetailDao
import com.example.satellites.database.detail.SatelliteDetailEntity
import com.example.satellites.database.list.SatelliteDao
import com.example.satellites.database.list.SatelliteEntity

@Database(entities = [SatelliteEntity::class, SatelliteDetailEntity::class], version = 1)
abstract class SatelliteDatabase : RoomDatabase() {
    abstract fun satelliteDao(): SatelliteDao
    abstract fun satelliteDetailDao(): SatelliteDetailDao
}