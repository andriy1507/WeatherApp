package com.goryachok.repository

import com.goryachok.core.SECOND_MS
import com.goryachok.core.model.Forecast
import com.goryachok.core.model.Weather
import com.goryachok.local.model.ForecastLocal
import com.goryachok.local.model.WeatherLocal
import com.goryachok.remote.model.ForecastRemote
import com.goryachok.remote.model.WeatherRemote

fun WeatherRemote.mapWeatherToLocal(): WeatherLocal {
    return WeatherLocal(
        city = city,
        temp = mainBodyRemote.temp,
        humidity = mainBodyRemote.humidity,
        pressure = mainBodyRemote.pressure,
        windSpd = wind.speed,
        windDir = wind.deg,
        timeStamp = date * SECOND_MS
    )
}

fun WeatherRemote.mapWeatherToDomain(): Weather {
    return Weather(
        city = city,
        temp = mainBodyRemote.temp,
        description = weather.first().description,
        humidity = mainBodyRemote.humidity,
        pressure = mainBodyRemote.pressure,
        windSpd = wind.speed,
        windDir = wind.deg,
        timeStamp = date * SECOND_MS
    )
}

fun WeatherLocal.mapWeatherToDomain(): Weather {
    return Weather(
        city = city,
        description = description,
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
                timeStamp = it.date * SECOND_MS
            )
        )
    }
    return ForecastLocal(city.name, city.country, mappedList)
}

fun ForecastRemote.mapForecastToDomain(): Forecast {
    val mapedList = mutableListOf<Weather>()
    list.forEach {
        mapedList.add(
            Weather(
                city = city.name,
                description = it.weather.first().description,
                temp = it.mainBodyRemote.temp,
                humidity = it.mainBodyRemote.humidity,
                pressure = it.mainBodyRemote.pressure,
                windSpd = it.wind.speed,
                windDir = it.wind.deg,
                timeStamp = it.date * SECOND_MS
            )
        )
    }
    return Forecast(city.name, city.country, mapedList)
}

fun ForecastLocal.mapForecastToDomain(): Forecast {
    val mappedList = mutableListOf<Weather>()
    weatherList.forEach {
        mappedList.add(it.mapWeatherToDomain())
    }
    return Forecast(
        city = city,
        country = country,
        weatherList = mappedList
    )
}