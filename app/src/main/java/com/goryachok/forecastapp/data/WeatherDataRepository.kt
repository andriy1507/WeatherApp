package com.goryachok.forecastapp.data

import android.content.Context
import org.threeten.bp.ZonedDateTime

class WeatherDataRepository(context: Context) {
    private val remote: RemoteDataSource = RemoteDataSource()
    private val local = LocalDataSource(context)

    private fun isFetchNeeden(lastFetchTime: Long): Boolean {
        val now = ZonedDateTime.now().toEpochSecond()
        return now - lastFetchTime > 60 * 60
    }

}