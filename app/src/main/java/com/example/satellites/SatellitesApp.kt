package com.example.satellites

import android.app.Application
import com.example.satellites.di.databaseModule
import com.example.satellites.di.mapperModule
import com.example.satellites.di.networkModule
import com.example.satellites.di.repositoryModule
import com.example.satellites.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SatellitesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SatellitesApp)
            modules(networkModule, repositoryModule, viewModelModule, mapperModule, databaseModule)
            androidLogger(Level.DEBUG)
        }
    }
}