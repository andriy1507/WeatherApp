package com.goryachok.forecastapp.model.forecast


import com.google.gson.annotations.SerializedName
import com.goryachok.forecastapp.model.forecast.City
import com.goryachok.forecastapp.model.forecast.Forecast

data class ForecastResponse(
    @SerializedName("city")
    val city: City = City(),
    @SerializedName("cnt")
    val cnt: Int = 0,
    @SerializedName("cod")
    val cod: String = "",
    @SerializedName("list")
    val list: List<Forecast> = listOf(),
    @SerializedName("message")
    val message: Int = 0
)