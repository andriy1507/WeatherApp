package com.goryachok.forecastapp.repository

import android.content.Context
import com.goryachok.forecastapp.base.HOUR_MS
import com.goryachok.forecastapp.base.SECOND_MS
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class WeatherRepository(context: Context) : BaseRepository<WeatherEntity>(context) {

    override suspend fun getRemoteDataByCoordinates(lat: Float, lon: Float): Result<WeatherEntity> =
        runBlocking {
            val result = async { remote.getWeatherByCoordinates(lat, lon) }
            result.await()
        }

    override suspend fun getRemoteDataByCity(city: String): Result<WeatherEntity> =
        runBlocking {
            val result = async { remote.getWeatherByCity(city) }
            result.await()
        }

    override fun getLocalData(): WeatherEntity =
        local.readWeatherData()


    override suspend fun getDataByCity(city: String): Result<WeatherEntity> =
        runBlocking {
            val result = async { remote.getWeatherByCity(city) }
            result.await()
        }


    override suspend fun getDataByCoordinates(lat: Float, lon: Float): Result<WeatherEntity> {
        return if (isFetchNeeded()) {
            getRemoteDataByCoordinates(lat, lon)
        } else {
            Result.Success(getLocalData())
        }

    }

    override fun isFetchNeeded(): Boolean {
        return if (local.isDataAvailable()) {
            val difference = System.currentTimeMillis() - local.readWeatherData().date * SECOND_MS
            return difference > HOUR_MS
        } else {
            true
        }
    }
}
