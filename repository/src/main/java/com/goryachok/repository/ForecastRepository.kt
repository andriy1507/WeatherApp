package com.goryachok.repository

import com.goryachok.local.LocalDataSource
import com.goryachok.local.model.LocalForecast
import com.goryachok.remote.RemoteDataSource
import com.goryachok.remote.model.ForecastEntity
import com.goryachok.remote.model.ApiResponse

interface ForecastRepository {

    val local: LocalDataSource

    val remote: RemoteDataSource

    suspend fun getRemoteDataByCoordinates(lat: Float, lon: Float): ApiResponse<ForecastEntity>

    suspend fun getRemoteDataByCity(city: String): ApiResponse<ForecastEntity>

    fun getLocalData(): LocalForecast

    suspend fun getDataByCoordinates(lat: Float, lon: Float): ResponseResult<LocalForecast>

    suspend fun getDataByCity(city: String): ResponseResult<LocalForecast>

    fun isFetchNeeded(): Boolean
}