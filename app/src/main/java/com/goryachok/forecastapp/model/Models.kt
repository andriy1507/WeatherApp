package com.goryachok.forecastapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Clouds(
    @SerializedName("all")
    @Expose
    var all: Int
)


class Coordinates(
    @SerializedName("lon")
    @Expose
    var lon: Double,
    @SerializedName("lat")
    @Expose
    var lat: Double
)


class Main(
    @SerializedName("temp")
    @Expose
    var temp: Int,
    @SerializedName("feels_like")
    @Expose
    var feelsLike: Double,
    @SerializedName("temp_min")
    @Expose
    var tempMin: Int,
    @SerializedName("temp_max")
    @Expose
    var tempMax: Int,
    @SerializedName("pressure")
    @Expose
    var pressure: Int,
    @SerializedName("humidity")
    @Expose
    var humidity: Int
)


class Sys(
    @SerializedName("type")
    @Expose
    var type: Int,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("country")
    @Expose
    var country: String,
    @SerializedName("sunrise")
    @Expose
    var sunrise: Int,
    @SerializedName("sunset")
    @Expose
    var sunset: Int
)

class Weather(
    @SerializedName("coord")
    @Expose
    var coord: Coordinates,
    @SerializedName("weather")
    @Expose
    var weather: List<WeatherDescr>,
    @SerializedName("base")
    @Expose
    var base: String,
    @SerializedName("main")
    @Expose
    var main: Main,
    @SerializedName("visibility")
    @Expose
    var visibility: Int,
    @SerializedName("wind")
    @Expose
    var wind: Wind,
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds,
    @SerializedName("dt")
    @Expose
    var dt: Int,
    @SerializedName("sys")
    @Expose
    var sys: Sys,
    @SerializedName("timezone")
    @Expose
    var timezone: Int,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("cod")
    @Expose
    var cod: Int
)

class WeatherDescr(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("main")
    @Expose
    var main: String,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("icon")
    @Expose
    var icon: String
    )

class Wind (
    @SerializedName("speed")
    @Expose
    var speed: Int,
    @SerializedName("deg")
    @Expose
    var deg: Int
)