package com.goryachok.forecastapp.repository

import android.content.Context
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.model.domain.RemoteEntity
import com.goryachok.forecastapp.model.local.Result


abstract class BaseRepository<T : RemoteEntity>(context: Context) {

    protected val remote by lazy { RemoteDataSource() }

    protected val local by lazy { LocalDataSource(context) }

    protected abstract suspend fun getRemoteDataByCoordinates(lat: Float, lon: Float): Result<T>

    protected abstract suspend fun getRemoteDataByCity(city: String): Result<T>

    protected abstract fun getLocalData(): T

    abstract suspend fun getDataByCoordinates(lat: Float, lon: Float): Result<T>

    abstract suspend fun getDataByCity(city: String): Result<T>

    protected abstract fun isFetchNeeded(): Boolean
}
