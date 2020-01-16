package com.goryachok.forecastapp.model.local

import android.os.Parcelable
import com.goryachok.forecastapp.model.domain.Weather
import com.goryachok.forecastapp.model.domain.Wind
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherModel(
val wind:Wind,
val weather:Weather
) : Parcelable