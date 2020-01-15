package com.goryachok.forecastapp.pojo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.goryachok.forecastapp.base.RemoteEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponse(
    val coordinates: Coordinates = Coordinates(),
    @SerializedName("dt")
    val date: Long = 0,
    @SerializedName("main")
    val main: Main = Main(),
    @SerializedName("name")
    val city: String = "",
    @SerializedName("timezone")
    val timezone: Int = 0,
    @SerializedName("weather")
    val weather: List<Weather> = listOf(Weather()),
    @SerializedName("wind")
    val wind: Wind = Wind()
) : Parcelable, RemoteEntity