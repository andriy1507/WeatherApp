package com.goryachok.forecastapp.base

import com.google.gson.Gson
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity

fun String.forecastFromJson(): ForecastEntity {
    return Gson().fromJson(this, ForecastEntity::class.java)
}

fun String.weatherFromJson(): WeatherEntity {
    return Gson().fromJson(this, WeatherEntity::class.java)
}