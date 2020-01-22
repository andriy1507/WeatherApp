package com.goryachok.forecastapp.data

import com.goryachok.forecastapp.api.ClientProvider
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.model.local.Result.Error
import com.goryachok.forecastapp.model.local.Result.Success
import okio.IOException

class RemoteDataSource {

    private val apiService by lazy { ClientProvider().create() }

    suspend fun getWeatherByCity(city: String): Result<WeatherEntity> {
        val response = apiService.getWeatherByCity(city)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { Success(data) } ?: Error(NullPointerException("Response data is null"))
        } else {
            Error(IOException(response.message()))
        }
    }

    suspend fun getForecastByCity(city: String): Result<ForecastEntity> {
        val response = apiService.getForecastByCity(city)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { Success(it) } ?: Error(NullPointerException("Response data is null"))
        } else {
            Error(IOException(response.message()))
        }
    }

    suspend fun getWeatherByCoord(lat: Float, lon: Float): Result<WeatherEntity> {
        val response = apiService.getWeatherByCoord(lat, lon)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { Success(data) } ?: Error(NullPointerException("Response data is null"))
        } else {
            Error(IOException(response.message()))
        }
    }

    suspend fun getForecastByCoord(lat: Float, lon: Float): Result<ForecastEntity> {
        val response = apiService.getForecastByCoord(lat, lon)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { Success(data) } ?: Error(NullPointerException("Response data is null"))
        } else {
            Error(IOException(response.message()))
        }
    }

}