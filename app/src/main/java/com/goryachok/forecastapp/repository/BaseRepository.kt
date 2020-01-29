package com.goryachok.forecastapp.repository

import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.model.domain.RemoteEntity
import com.goryachok.forecastapp.model.local.Result


interface BaseRepository<T : RemoteEntity> {

    val remote: RemoteDataSource

    val local: LocalDataSource

    suspend fun getRemoteDataByCoordinates(lat: Float, lon: Float): Result<T>

    suspend fun getRemoteDataByCity(city: String): Result<T>

    fun getLocalData(): T

    suspend fun getDataByCoordinates(lat: Float, lon: Float): Result<T>

    suspend fun getDataByCity(city: String): Result<T>

    fun isFetchNeeded(): Boolean
}
