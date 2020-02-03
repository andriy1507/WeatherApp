package com.goryachok.repository

import com.goryachok.core.base.ForecastRepository
import com.goryachok.local.LocalDataSource
import com.goryachok.remote.RemoteDataSource
import javax.inject.Inject


class ForecastRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ForecastRepository {

    override fun getDataByCity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataByCoordinates() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}