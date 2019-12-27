package com.goryachok.forecastapp.model


import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("city")
    val city: City = City(),
    @SerializedName("list")
    val list: List<Forecast> = listOf(Forecast())
)