package com.goryachok.forecastapp.repository

import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.di.repository.DaggerRepositoryComponent
import javax.inject.Inject


class Repository {

    @Inject
    lateinit var remote:RemoteDataSource

    @Inject
    lateinit var local:LocalDataSource

    init {
        DaggerRepositoryComponent.create().inject(this)
    }
}
