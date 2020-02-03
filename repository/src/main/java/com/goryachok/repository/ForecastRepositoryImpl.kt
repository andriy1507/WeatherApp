package com.goryachok.repository

import com.goryachok.core.HOUR_MS
import com.goryachok.core.model.ResponseResult
import com.goryachok.core.repository.ForecastRepository
import com.goryachok.local.LocalDataSource
import com.goryachok.local.model.WeatherLocal
import com.goryachok.remote.RemoteDataSource
import com.goryachok.remote.model.ApiResponse
import com.goryachok.remote.model.ForecastRemote
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class ForecastRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ForecastRepository {

    private suspend fun getRemoteDataByCoordinates(
        lat: Float,
        lon: Float
    ): ApiResponse<ForecastRemote> =
        runBlocking {
            val result = async { remote.getForecastByCoordinates(lat, lon) }
            val await = result.await()
            if (await is ApiResponse.Success)
                local.saveForecastData(await.data.mapForecastToLocal())
            return@runBlocking await
        }

    private fun getLocalData(): WeatherLocal =
        local.readWeatherData()


    override suspend fun getDataByCity(city: String): ResponseResult<*> =
        runBlocking {
            val result = async { remote.getForecastByCity(city) }
            when (val await = result.await()) {
                is ApiResponse.Success -> ResponseResult.Success(await.data.mapForecastToDomain())
                is ApiResponse.Error -> ResponseResult.Error(await.exception)
            }
        }


    override suspend fun getDataByCoordinates(lat: Float, lon: Float): ResponseResult<*> {
        return if (isFetchNeeded()) {
            when (val response = getRemoteDataByCoordinates(lat, lon)) {
                is ApiResponse.Success -> ResponseResult.Success(response.data.mapForecastToDomain())
                is ApiResponse.Error -> ResponseResult.Error(response.exception)
            }
        } else {
            ResponseResult.Success(getLocalData().mapWeatherToDomain())
        }

    }

    private fun isFetchNeeded(): Boolean {
        return if (local.isDataAvailable()) {
            val difference = System.currentTimeMillis() - local.readWeatherData().timeStamp
            return difference > HOUR_MS
        } else {
            true
        }
    }
}