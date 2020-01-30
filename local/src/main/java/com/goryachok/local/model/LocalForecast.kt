package com.goryachok.local.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocalForecast(
    val city: String,
    val country: String,
    val weatherList: List<LocalWeather>
) : Parcelable,LocalData


fun LocalForecast.toJson(): String? = Gson().toJson(this, LocalForecast::class.java)

fun String.forecastFromJson(): LocalForecast =
    Gson().fromJson<LocalForecast>(this, LocalForecast::class.java)
