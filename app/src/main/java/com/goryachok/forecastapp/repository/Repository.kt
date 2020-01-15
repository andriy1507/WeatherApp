package com.goryachok.forecastapp.repository

import android.content.Context
import com.goryachok.forecastapp.base.RemoteEntity
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.pojo.ForecastEntity
import com.goryachok.forecastapp.pojo.WeatherEntity
import kotlin.reflect.KClass

class Repository(context: Context) {

    private val remote: RemoteDataSource = RemoteDataSource()
    private val local = LocalDataSource(context)

    private fun needFetch() = if (local.isDataAvailable()) {
        val lastUpdate = local.getWeatherData().date
        val now = System.nanoTime()
        now - lastUpdate > 60 * 60
    } else true

    fun <T : RemoteEntity> getDataByCoordinates(
        lon: Float,
        lat: Float,
        type: KClass<T>
    ): RemoteEntity {
        if (needFetch()) {
            val remoteData: RemoteEntity = when (type) {
                WeatherEntity::class -> {
                    remote.getWeatherData(lon = lon, lat = lat)
                }
                ForecastEntity::class -> {
                    remote.getForecastData(lon = lon, lat = lat)
                }
                else -> throw ClassCastException()
            }
            local.saveData(remoteData)
            return remoteData
        } else {
            return when (type) {
                WeatherEntity::class -> local.getWeatherData()
                ForecastEntity::class -> local.getForecastData()
                else -> throw ClassCastException()
            }
        }
    }


    fun <T : RemoteEntity> getDataByCity(city: String, type: KClass<T>): RemoteEntity =
        when (type) {
            WeatherEntity::class -> remote.getWeatherData(city = city)
            ForecastEntity::class -> remote.getForecastData(city = city)
            else -> throw ClassCastException()
        }
}