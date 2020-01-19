package com.goryachok.forecastapp.repository

import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.di.repository.DaggerRepositoryComponent
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result.Error
import com.goryachok.forecastapp.model.local.Result.Success
import javax.inject.Inject


class Repository @Inject constructor(val remote: RemoteDataSource, val local: LocalDataSource) {

    init {
        DaggerRepositoryComponent.create().inject(this)
    }

    suspend fun getWeatherData(city: String): WeatherEntity{
        val data: WeatherEntity
        when (val result = remote.getWeatherByCity(city)) {
            is Success -> {
                data = result.data
                return data
            }
            is Error -> {
                throw result.exception
            }
        }
    }
}
