package com.goryachok.forecastapp.repository

import android.content.Context
import com.goryachok.forecastapp.base.HOUR_MS
import com.goryachok.forecastapp.base.SECOND_MS
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.local.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ForecastRepository(context: Context) : BaseRepository<ForecastEntity>(context) {
    override suspend fun getRemoteDataByCoordinates(
        lat: Float,
        lon: Float
    ): Result<ForecastEntity> =
        runBlocking {
            val result = async { remote.getForecastByCoordinates(lat, lon) }
            result.await()
        }

    override suspend fun getRemoteDataByCity(city: String): Result<ForecastEntity> =
        runBlocking {
            val result = async { remote.getForecastByCity(city) }
            result.await()
        }

    override fun getLocalData(): ForecastEntity =
        local.readForecastData()


    override suspend fun getDataByCity(city: String): Result<ForecastEntity> =
        runBlocking {
            val result = async { remote.getForecastByCity(city) }
            result.await()
        }


    override suspend fun getDataByCoordinates(lat: Float, lon: Float): Result<ForecastEntity> {
        return if (isFetchNeeded()) {
            getRemoteDataByCoordinates(lat, lon)
        } else {
            Result.Success(getLocalData())
        }

    }

    override fun isFetchNeeded(): Boolean {
        return if (local.isDataAvailable()) {
            val difference = local.readWeatherData().date * SECOND_MS - System.currentTimeMillis()
            return difference > HOUR_MS
        } else {
            true
        }
    }
}