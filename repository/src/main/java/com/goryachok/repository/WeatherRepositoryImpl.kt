package com.goryachok.repository

import com.goryachok.local.LocalDataSource
import com.goryachok.local.model.LocalWeather
import com.goryachok.remote.RemoteDataSource
import com.goryachok.remote.model.ApiResponse
import com.goryachok.remote.model.WeatherEntity

class WeatherRepositoryImpl(
    override val local: LocalDataSource,
    override val remote: RemoteDataSource
) :WeatherRepository {
    override suspend fun getRemoteDataByCoordinates(lat: Float, lon: Float): ApiResponse<WeatherEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getRemoteDataByCity(city: String): ApiResponse<WeatherEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocalData(): LocalWeather {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getDataByCoordinates(lat: Float, lon: Float): ResponseResult<LocalWeather> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getDataByCity(city: String): ResponseResult<LocalWeather> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isFetchNeeded(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}