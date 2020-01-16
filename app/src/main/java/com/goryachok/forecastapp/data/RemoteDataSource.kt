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

//    fun getWeatherData(
//        city: String? = null,
//        lon: Float = 0.0F,
//        lat: Float = 0.0F
//    ): WeatherEntity {
//        lateinit var response: WeatherEntity
//        if (city != null) {
//            CoroutineScope(IO).launch {
//                response = api.getWeatherByCityAsync(city)
//            }
//        } else {
//            CoroutineScope(IO).launch {
//                response = api.getWeatherByCoordinatesAsync(lon, lat)
//            }
//        }
//        return response
//    }
//
//    fun getForecastData(
//        city: String? = null,
//        lon: Float = 0.0F,
//        lat: Float = 0.0F
//    ): ForecastEntity {
//        lateinit var response: ForecastEntity
//        if (city != null) {
//            CoroutineScope(IO).launch {
//                response = api.getForecastByCityAsync(city)
//            }
//        } else {
//            CoroutineScope(IO).launch {
//                response = api.getForecastByCoordinatesAsync(lon, lat)
//            }
//        }
//        return response
//    }

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
        lateinit var response: RemoteEntity
        if (city != null) {
                response = api.getDataByCityAsync(type, city).await()
        } else {
                response = api.getDataByCoordinatesAsync(type, lon, lat).await()
        }
        return response as T
    }
}