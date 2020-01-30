package com.goryachok.remote.model

sealed class ApiResponse<out T : Any> {
    data class Success<out T : RemoteEntity>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Exception) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}