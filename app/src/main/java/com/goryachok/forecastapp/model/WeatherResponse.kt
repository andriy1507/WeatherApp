package com.goryachok.forecastapp.model


import com.google.gson.annotations.SerializedName

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
)