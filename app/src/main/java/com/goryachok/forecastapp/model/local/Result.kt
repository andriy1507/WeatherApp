package com.goryachok.forecastapp.model.local

import com.goryachok.forecastapp.model.domain.RemoteEntity

sealed class Result<out T : Any> {
    data class Success<out T : RemoteEntity>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}