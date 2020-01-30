package com.goryachok.local.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocalWeather(
    val city: String,
    val temp: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpd: Double,
    val windDir: Int,
    val timeStamp: Long
) : Parcelable,LocalData

fun LocalWeather.toJson(): String? = Gson().toJson(this, LocalWeather::class.java)


fun String.weatherFromJson(): LocalWeather =
    Gson().fromJson<LocalWeather>(this, LocalWeather::class.java)
