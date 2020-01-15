package com.goryachok.forecastapp.pojo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.goryachok.forecastapp.base.RemoteEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherEntity(
    val coordinates: Coordinates,
    @SerializedName("dt")
    val date: Long,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val city: String,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) : Parcelable, RemoteEntity