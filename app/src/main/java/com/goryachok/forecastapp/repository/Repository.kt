package com.goryachok.forecastapp.repository

import android.content.Context
import com.goryachok.forecastapp.base.RemoteEntity
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.pojo.ForecastEntity
import com.goryachok.forecastapp.pojo.WeatherEntity

class Repository(context: Context) {

    val remote: RemoteDataSource = RemoteDataSource()
    val local = LocalDataSource(context)

    fun needFetch() = if (local.isDataAvailable()) {
        val lastUpdate = local.getWeatherData().date
        val now = System.nanoTime()
        now - lastUpdate > 60 * 60
    } else true

    inline fun <reified T : RemoteEntity> getDataByCoordinates(
        lon: Float,
        lat: Float
    ): RemoteEntity =
        if (needFetch()) {
            when (T::class) {
                WeatherEntity::class -> remote.getWeatherData(lon = lon, lat = lat)
                ForecastEntity::class -> remote.getForecastData(lon = lon, lat = lat)
                else -> throw ClassCastException()
            }
        } else {
            when (T::class) {
                WeatherEntity::class -> local.getWeatherData()
                ForecastEntity::class -> local.getForecastData()
                else -> throw ClassCastException()
            }
        }


    inline fun <reified T : RemoteEntity> getDataByCity(city: String): RemoteEntity =
        when (T::class) {
            WeatherEntity::class -> remote.getWeatherData(city = city)
            ForecastEntity::class -> remote.getForecastData(city = city)
            else -> throw ClassCastException()
        }
}