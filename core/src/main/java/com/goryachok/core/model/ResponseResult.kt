package com.goryachok.core.model

sealed class ResponseResult<T : Any> {
    data class Success<T : DomainEntity>(val data: T) : ResponseResult<DomainEntity>()
    class Error(val exception: Exception) :
        ResponseResult<Exception>()

    object Loading : ResponseResult<Nothing>()
}