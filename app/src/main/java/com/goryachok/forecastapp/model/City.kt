package com.goryachok.forecastapp.model


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("coord")
    val coordinates: Coordinates = Coordinates(),
    @SerializedName("country")
    val country: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("sunrise")
    val sunrise: Int = 0,
    @SerializedName("sunset")
    val sunset: Int = 0,
    @SerializedName("timezone")
    val timezone: Int = 0
)