package com.goryachok.repository

import com.goryachok.core.model.ForecastDomain
import com.goryachok.core.model.WeatherDomain
import com.goryachok.local.model.ForecastLocal
import com.goryachok.local.model.WeatherLocal
import com.goryachok.remote.model.ForecastRemote
import com.goryachok.remote.model.WeatherRemote

private const val SECOND = 1000L

fun WeatherRemote.mapWeatherToLocal(): WeatherLocal {
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

fun WeatherRemote.mapWeatherToDomain(): WeatherDomain {
    return WeatherDomain(
        city = city,
        temp = mainBodyRemote.temp,
        humidity = mainBodyRemote.humidity,
        pressure = mainBodyRemote.pressure,
        windSpd = wind.speed,
        windDir = wind.deg,
        timeStamp = date * SECOND
    )
}

fun WeatherLocal.mapWeatherToDomain(): WeatherDomain {
    return WeatherDomain(
        city = city,
        temp = temp,
        humidity = humidity,
        pressure = pressure,
        windSpd = windSpd,
        windDir = windDir,
        timeStamp = timeStamp
    )
}

fun ForecastRemote.mapForecastToLocal(): ForecastLocal {
    val mappedList = mutableListOf<WeatherLocal>()
    list.forEach {
        mappedList.add(
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
    return ForecastLocal(city.name, city.country, mappedList)
}

fun ForecastRemote.mapForecastToDomain(): ForecastDomain {
    val mapedList = mutableListOf<WeatherDomain>()
    list.forEach {
        mapedList.add(
            WeatherDomain(
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
    return ForecastDomain(city.name, city.country, mapedList)
}

fun ForecastLocal.mapForecastToDomain(): ForecastDomain {
    val mappedList = mutableListOf<WeatherDomain>()
    weatherList.forEach {
        mappedList.add(it.mapWeatherToDomain())
    }
    return ForecastDomain(
        city = city,
        country = country,
        weatherList = mappedList
    )
}