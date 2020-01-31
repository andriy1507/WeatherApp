package com.goryachok.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    @SerializedName("dt")
    val date: Int,
    @SerializedName("dt_txt")
    val dateText: String,
    @SerializedName("main")
    val mainBodyRemote: MainBodyRemote,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) : Parcelable, RemoteEntity()