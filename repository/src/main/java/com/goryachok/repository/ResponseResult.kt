package com.goryachok.repository

import com.goryachok.core.model.DomainEntity

sealed class ResponseResult<T : Any> {
    data class Success<T : DomainEntity>(val data: T) : ResponseResult<DomainEntity>()
    class Error(val exception: Exception):ResponseResult<Exception>()
    object Loading:ResponseResult<Nothing>()
}