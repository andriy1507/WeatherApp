package com.goryachok.core.repository

import com.goryachok.core.model.ResponseResult

interface WeatherRepository {

    suspend fun getDataByCity(city: String): ResponseResult<*>

    suspend fun getDataByCoordinates(lat: Float, lon: Float): ResponseResult<*>
}