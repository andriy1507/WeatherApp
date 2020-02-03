package com.goryachok.local.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherLocal(
    val city: String,
    val description: String = "",
    val temp: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpd: Double,
    val windDir: Int,
    val timeStamp: Long
) : Parcelable, LocalEntity

fun WeatherLocal.toJson(): String? = Gson().toJson(this, WeatherLocal::class.java)


fun String.weatherFromJson(): WeatherLocal =
    Gson().fromJson<WeatherLocal>(this, WeatherLocal::class.java)
