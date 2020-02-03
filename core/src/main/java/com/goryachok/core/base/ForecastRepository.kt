package com.goryachok.core.base

interface ForecastRepository {

    fun getDataByCity()

    fun getDataByCoordinates()
}