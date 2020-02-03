package com.goryachok.core.base

import com.goryachok.core.model.ResponseResult

interface ForecastRepository {

    suspend fun getDataByCity(city: String): ResponseResult<*>

    suspend fun getDataByCoordinates(lat: Float, lon: Float): ResponseResult<*>
}