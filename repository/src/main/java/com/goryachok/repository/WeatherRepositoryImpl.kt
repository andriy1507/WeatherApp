package com.goryachok.repository

import com.goryachok.core.base.WeatherRepository
import com.goryachok.local.LocalDataSource
import com.goryachok.remote.RemoteDataSource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : WeatherRepository {

    companion object {
        private const val SECOND_MS = 1000
        private const val MINUTE_MS = 60 * SECOND_MS
        private const val HOUR_MS = 60 * MINUTE_MS
    }

    override suspend fun getDataByCity(city: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getDataByCoordinates(lat: Float, lon: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}