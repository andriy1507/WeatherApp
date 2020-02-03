package com.goryachok.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherRemote(
    val coordinates: CoordinatesRemote,
    @SerializedName("dt")
    val date: Long,
    @SerializedName("main")
    val mainBodyRemote: MainBodyRemote,
    @SerializedName("name")
    val city: String,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("weather")
    val weather: List<DescriptionRemote>,
    @SerializedName("wind")
    val wind: WindRemote
) : Parcelable, RemoteEntity()