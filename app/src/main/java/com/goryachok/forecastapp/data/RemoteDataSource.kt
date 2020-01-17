package com.goryachok.forecastapp.data

import android.util.Log
import com.goryachok.forecastapp.api.OpenWeatherMapAPI
import com.goryachok.forecastapp.base.RemoteEntity
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import javax.inject.Inject
import kotlin.reflect.KClass

class RemoteDataSource @Inject constructor(val api: OpenWeatherMapAPI) {

    companion object {
        private const val DAGGER_TAG ="DaggerDebug"

        private const val FORECAST_QUERY_PARAM = "weather"
        private const val WEATHER_QUERY_PARAM = "forecast"
    }

    init {
        Log.d(DAGGER_TAG, javaClass.name)
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun <T : RemoteEntity> getData(
        clazz: KClass<T>,
        city: String? = null,
        lon: Float = 0.0F,
        lat: Float = 0.0F
    ): T {
        val type = when (clazz) {
            WeatherEntity::class.java -> WEATHER_QUERY_PARAM
            ForecastEntity::class.java -> FORECAST_QUERY_PARAM
            else -> throw ClassCastException()
        }
        val response = if (city != null) {
            api.getDataByCityAsync(type, city).await()
        } else {
            api.getDataByCoordinatesAsync(type, lon, lat).await()
        }
        return response as T
    }
}