package com.goryachok.forecastapp.model


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("dt")
    val date: Int = 0,
    @SerializedName("dt_txt")
    val dateText: String = "",
    @SerializedName("main")
    val main: Main = Main(),
    @SerializedName("weather")
    val weather: List<Weather> = listOf(Weather()),
    @SerializedName("wind")
    val wind: Wind = Wind()
)