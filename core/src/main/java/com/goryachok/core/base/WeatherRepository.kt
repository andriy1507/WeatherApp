package com.goryachok.core.base

interface WeatherRepository {

    suspend fun getDataByCity(city: String)

    suspend fun getDataByCoordinates(lat: Float, lon: Float)
}