package com.goryachok.repository

import com.goryachok.local.model.ForecastLocal
import com.goryachok.local.model.WeatherLocal
import com.goryachok.remote.model.ForecastEntity
import com.goryachok.remote.model.WeatherEntity

class Mapper {

    companion object {
        private const val SECOND = 1000L
    }

    fun WeatherEntity.mapWeatherToLocal(): WeatherLocal {
        return WeatherLocal(
            city = city,
            temp = mainBodyRemote.temp,
            humidity = mainBodyRemote.humidity,
            pressure = mainBodyRemote.pressure,
            windSpd = wind.speed,
            windDir = wind.deg,
            timeStamp = date * SECOND
        )
    }

    fun ForecastEntity.mapForecastToLocal(): ForecastLocal {
        val mapedList = mutableListOf<WeatherLocal>()
        list.forEach {
            mapedList.add(
                WeatherLocal(
                    city = city.name,
                    description = it.weather.first().description,
                    temp = it.mainBodyRemote.temp,
                    humidity = it.mainBodyRemote.humidity,
                    pressure = it.mainBodyRemote.pressure,
                    windSpd = it.wind.speed,
                    windDir = it.wind.deg,
                    timeStamp = it.date * SECOND
                )
            )
        }
        return ForecastLocal(city.name, city.country, mapedList)
    }
}