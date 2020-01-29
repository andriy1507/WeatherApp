package com.goryachok.forecastapp.data

import com.goryachok.forecastapp.api.WeatherApiService
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.model.local.Result.Error
import com.goryachok.forecastapp.model.local.Result.Success
import okio.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(override val apiService: WeatherApiService) :
    RemoteDataSource {

    override suspend fun getWeatherByCity(city: String): Result<WeatherEntity> {
        val response = apiService.getWeatherByCity(city)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { Success(data) } ?: Error(NullPointerException("Response data is null"))
        } else {
            Error(IOException(response.message()))
        }
    }

    override suspend fun getForecastByCity(city: String): Result<ForecastEntity> {
        val response = apiService.getForecastByCity(city)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { Success(it) } ?: Error(NullPointerException("Response data is null"))
        } else {
            Error(IOException(response.message()))
        }
    }

    override suspend fun getWeatherByCoordinates(lat: Float, lon: Float): Result<WeatherEntity> {
        val response = apiService.getWeatherByCoordinates(lat, lon)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { Success(data) } ?: Error(NullPointerException("Response data is null"))
        } else {
            Error(IOException(response.message()))
        }
    }

    override suspend fun getForecastByCoordinates(lat: Float, lon: Float): Result<ForecastEntity> {
        val response = apiService.getForecastByCoordinates(lat, lon)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { Success(data) } ?: Error(NullPointerException("Response data is null"))
        } else {
            Error(IOException(response.message()))
        }
    }
}