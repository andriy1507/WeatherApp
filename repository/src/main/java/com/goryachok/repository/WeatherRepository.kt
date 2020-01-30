package com.goryachok.repository

import com.goryachok.local.LocalDataSource
import com.goryachok.local.model.LocalWeather
import com.goryachok.remote.RemoteDataSource
import com.goryachok.remote.model.ApiResponse
import com.goryachok.remote.model.WeatherEntity


interface WeatherRepository {

    val local: LocalDataSource

    val remote: RemoteDataSource

    suspend fun getRemoteDataByCoordinates(lat: Float, lon: Float): ApiResponse<WeatherEntity>

    suspend fun getRemoteDataByCity(city: String): ApiResponse<WeatherEntity>

    fun getLocalData(): LocalWeather

    suspend fun getDataByCoordinates(lat: Float, lon: Float): ResponseResult<LocalWeather>

    suspend fun getDataByCity(city: String): ResponseResult<LocalWeather>

    fun isFetchNeeded(): Boolean
}