package com.goryachok.forecastapp.data

import androidx.lifecycle.LiveData
import com.goryachok.forecastapp.api.WeatherApiService
import com.goryachok.forecastapp.di.api.DaggerApiComponent
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.network.LiveDataCallAdapter
import javax.inject.Inject

class RemoteDataSource {

    @Inject
    lateinit var apiService: WeatherApiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    suspend fun getWeatherByCity(city: String): LiveData<WeatherEntity> {
        val call = apiService.getWeatherByCity(city)
        return LiveDataCallAdapter<WeatherEntity>()
            .adapt(call)
    }

    suspend fun getForecastByCity(city: String): LiveData<ForecastEntity> {
        val call = apiService.getForecastByCity(city)
        return LiveDataCallAdapter<ForecastEntity>()
            .adapt(call)
    }

}