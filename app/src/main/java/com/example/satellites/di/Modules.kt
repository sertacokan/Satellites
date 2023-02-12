package com.example.satellites.di

import androidx.room.Room
import com.example.satellites.database.SatelliteDatabase
import com.example.satellites.mapper.SatelliteDetailEntityMapper
import com.example.satellites.mapper.SatelliteEntityMapper
import com.example.satellites.network.MockService
import com.example.satellites.network.adapter.NetworkStateCallAdapterFactory
import com.example.satellites.network.mock.MockServerInterceptor
import com.example.satellites.repository.SatelliteDetailRepository
import com.example.satellites.repository.SatelliteListRepository
import com.example.satellites.repository.SatellitePositionRepository
import com.example.satellites.ui.fragment.detail.SatelliteDetailViewModel
import com.example.satellites.ui.fragment.list.SatelliteListViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        Moshi.Builder()
            .build()
    }
    single {
        MockServerInterceptor(androidContext(), get())
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<MockServerInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // We have to use real url otherwise we get timeout exception.
            .client(get())
            .addCallAdapterFactory(NetworkStateCallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    single {
        get<Retrofit>().create(MockService::class.java)
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), SatelliteDatabase::class.java, "satellites.db")
            .build()
    }
    single {
        get<SatelliteDatabase>().satelliteDetailDao()
    }
    single {
        get<SatelliteDatabase>().satelliteDao()
    }
}

val repositoryModule = module {
    singleOf(::SatelliteListRepository)
    singleOf(::SatelliteDetailRepository)
    singleOf(::SatellitePositionRepository)
}

val viewModelModule = module {
    viewModelOf(::SatelliteListViewModel)
    viewModel { parametersHolder ->
        SatelliteDetailViewModel(
            detailRepository = get(),
            positionRepository = get(),
            detailEntityMapper = get(),
            parametersHolder.get()
        )
    }
}

val mapperModule = module {
    singleOf(::SatelliteEntityMapper)
    singleOf(::SatelliteDetailEntityMapper)
}