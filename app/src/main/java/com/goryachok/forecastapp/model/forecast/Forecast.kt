package com.goryachok.forecastapp.model.forecast


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("clouds")
    val clouds: Clouds = Clouds(),
    @SerializedName("dt")
    val dt: Int = 0,
    @SerializedName("dt_txt")
    val dtTxt: String = "",
    @SerializedName("main")
    val main: Main = Main(),
    @SerializedName("sys")
    val sys: Sys = Sys(),
    @SerializedName("weather")
    val weather: List<Weather> = listOf(),
    @SerializedName("wind")
    val wind: Wind = Wind()
)