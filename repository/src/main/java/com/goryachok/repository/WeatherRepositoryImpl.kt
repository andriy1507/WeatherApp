package com.goryachok.repository

import com.goryachok.core.base.WeatherRepository
import com.goryachok.core.model.ResponseResult
import com.goryachok.local.LocalDataSource
import com.goryachok.local.model.WeatherLocal
import com.goryachok.remote.RemoteDataSource
import com.goryachok.remote.model.ApiResponse
import com.goryachok.remote.model.WeatherRemote
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : WeatherRepository {

    companion object {
        private const val SECOND_MS = 1000
        private const val MINUTE_MS = 60 * SECOND_MS
        private const val HOUR_MS = 60 * MINUTE_MS
    }

    private suspend fun getRemoteDataByCoordinates(
        lat: Float,
        lon: Float
    ): ApiResponse<WeatherRemote> =
        runBlocking {
            val result = async { remote.getWeatherByCoordinates(lat, lon) }
            val await = result.await()
            if (await is ApiResponse.Success)
                local.saveWeatherData(await.data.mapWeatherToLocal())
            return@runBlocking await
        }

    private fun getLocalData(): WeatherLocal =
        local.readWeatherData()


    override suspend fun getDataByCity(city: String): ResponseResult<*> =
        runBlocking {
            val result = async { remote.getWeatherByCity(city) }
            when (val await = result.await()) {
                is ApiResponse.Success -> ResponseResult.Success(await.data.mapWeatherToDomain())
                is ApiResponse.Error -> ResponseResult.Error(await.exception)
            }
        }


    override suspend fun getDataByCoordinates(lat: Float, lon: Float): ResponseResult<*> {
        return if (isFetchNeeded()) {
            when (val response = getRemoteDataByCoordinates(lat, lon)) {
                is ApiResponse.Success -> ResponseResult.Success(response.data.mapWeatherToDomain())
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