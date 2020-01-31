package com.goryachok.repository

import com.goryachok.core.base.WeatherRepository
import com.goryachok.local.LocalDataSource
import com.goryachok.remote.RemoteDataSource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : WeatherRepository