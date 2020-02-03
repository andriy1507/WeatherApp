package com.goryachok.remote

import com.goryachok.remote.model.ApiResponse
import com.goryachok.remote.model.ForecastRemote
import com.goryachok.remote.model.WeatherRemote
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: WeatherApiService) :
    RemoteDataSource {
    override suspend fun getWeatherByCity(city: String): ApiResponse<WeatherRemote> {
        val response = api.getWeatherByCity(city)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { ApiResponse.Success(data) }
                ?: ApiResponse.Error(NullPointerException("Response data is null"))
        } else {
            ApiResponse.Error(IOException(response.message()))
        }
    }

    override suspend fun getForecastByCity(city: String): ApiResponse<ForecastRemote> {
        val response = api.getForecastByCity(city)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { ApiResponse.Success(it) }
                ?: ApiResponse.Error(NullPointerException("Response data is null"))
        } else {
            ApiResponse.Error(IOException(response.message()))
        }
    }

    override suspend fun getWeatherByCoordinates(
        lat: Float,
        lon: Float
    ): ApiResponse<WeatherRemote> {
        val response = api.getWeatherByCoordinates(lat, lon)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { ApiResponse.Success(data) }
                ?: ApiResponse.Error(NullPointerException("Response data is null"))
        } else {
            ApiResponse.Error(IOException(response.message()))
        }
    }

    override suspend fun getForecastByCoordinates(
        lat: Float,
        lon: Float
    ): ApiResponse<ForecastRemote> {
        val response = api.getForecastByCoordinates(lat, lon)
        return if (response.isSuccessful) {
            val data = response.body()
            data?.let { ApiResponse.Success(data) }
                ?: ApiResponse.Error(NullPointerException("Response data is null"))
        } else {
            ApiResponse.Error(IOException(response.message()))
        }
    }
}