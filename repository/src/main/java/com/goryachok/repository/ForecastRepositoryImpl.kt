package com.goryachok.repository

import com.goryachok.core.base.ForecastRepository
import com.goryachok.local.LocalDataSource
import com.goryachok.remote.RemoteDataSource
import javax.inject.Inject


class ForecastRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ForecastRepository