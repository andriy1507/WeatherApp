package com.goryachok.forecastapp.data

import com.goryachok.forecastapp.api.WeatherApiService
import com.goryachok.forecastapp.di.api.DaggerApiComponent
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.model.local.Result.Error
import com.goryachok.forecastapp.model.local.Result.Success
import okio.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: WeatherApiService) {

    init {
        DaggerApiComponent.create().inject(this)
    }

    suspend fun getWeatherByCity(city: String): Result<WeatherEntity> {
        val response = apiService.getWeatherByCity(city)
        return if (response.isSuccessful){
            val data = response.body()
            Success(data!!)
        }else{
            Error(IOException(response.message()))
        }
    }

    suspend fun getForecastByCity(city: String): Result<ForecastEntity> {
        val response = apiService.getForecastByCity(city)
        return if (response.isSuccessful){
            val data = response.body()
            Success(data!!)
        }else{
            Error(IOException(response.message()))
        }
    }

    suspend fun getWeatherByCoord(lat: Float, lon: Float): Result<WeatherEntity> {
        val response = apiService.getWeatherByCoord(lat,lon)
        return if (response.isSuccessful){
            val data = response.body()
            Success(data!!)
        }else{
            Error(IOException(response.message()))
        }
    }

    suspend fun getForecastByCoord(lat: Float, lon: Float): Result<ForecastEntity> {
        val response = apiService.getForecastByCoord(lat,lon)
        return if (response.isSuccessful){
            val data = response.body()
            Success(data!!)
        }else{
            Error(IOException(response.message()))
        }
    }

}