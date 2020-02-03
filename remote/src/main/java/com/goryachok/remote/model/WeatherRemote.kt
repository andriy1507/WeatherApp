package com.goryachok.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherRemote(
    val coordinates: Coordinates,
    @SerializedName("dt")
    val date: Long,
    @SerializedName("main")
    val mainBodyRemote: MainBodyRemote,
    @SerializedName("name")
    val city: String,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) : Parcelable, RemoteEntity()