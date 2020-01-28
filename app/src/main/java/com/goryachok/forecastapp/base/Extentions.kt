package com.goryachok.forecastapp.base

import com.google.gson.Gson
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import java.text.SimpleDateFormat
import java.util.*

fun String.forecastFromJson(): ForecastEntity {
    return Gson().fromJson(this, ForecastEntity::class.java)
}

fun String.weatherFromJson(): WeatherEntity {
    return Gson().fromJson(this, WeatherEntity::class.java)
}

fun <T> Collection<T>.getEveryEighth() = filterIndexed { index, _ -> index % 8 == 0 }

fun Int.getTime(): String {
    val format = SimpleDateFormat("HH:mm")
    val date = Date(this * SECOND_MS)
    return format.format(date)
}

fun Int.getDate(): String {
    val format = SimpleDateFormat("dd.MM")
    val date = Date(this * SECOND_MS)
    return format.format(date)
}
