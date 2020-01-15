package com.goryachok.forecastapp.pojo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    @SerializedName("dt")
    val date: Int = 0,
    @SerializedName("dt_txt")
    val dateText: String = "",
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("weather")
    val weather: List<Weather> = listOf(Weather()),
    @SerializedName("wind")
    val wind: Wind = Wind()
) : Parcelable