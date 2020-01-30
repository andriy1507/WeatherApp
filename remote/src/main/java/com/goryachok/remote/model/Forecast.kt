package com.goryachok.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.goryachok.remote.model.Main
import com.goryachok.remote.model.Weather
import com.goryachok.remote.model.Wind
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    @SerializedName("dt")
    val date: Int,
    @SerializedName("dt_txt")
    val dateText: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) : Parcelable