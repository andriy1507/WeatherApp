package com.goryachok.repository

import com.goryachok.local.LocalDataSource
import com.goryachok.local.model.LocalForecast
import com.goryachok.remote.RemoteDataSource
import com.goryachok.remote.model.ForecastEntity
import com.goryachok.remote.model.ApiResponse

class ForecastRepositoryImpl(
    override val local: LocalDataSource,
    override val remote: RemoteDataSource
) :ForecastRepository {

    override suspend fun getRemoteDataByCoordinates(
        lat: Float,
        lon: Float
    ): ApiResponse<ForecastEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getRemoteDataByCity(city: String): ApiResponse<ForecastEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocalData(): LocalForecast {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getDataByCoordinates(lat: Float, lon: Float): ResponseResult<LocalForecast> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getDataByCity(city: String): ResponseResult<LocalForecast> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isFetchNeeded(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}