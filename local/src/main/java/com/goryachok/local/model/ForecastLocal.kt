package com.goryachok.local.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastLocal(
    val city: String,
    val country: String,
    val weatherList: List<WeatherLocal>
) : Parcelable, LocalEntity


fun ForecastLocal.toJson(): String? = Gson().toJson(this, ForecastLocal::class.java)

fun String.forecastFromJson(): ForecastLocal =
    Gson().fromJson<ForecastLocal>(this, ForecastLocal::class.java)
