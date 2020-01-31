package com.goryachok.repository

import com.goryachok.local.model.LocalEntity

sealed class ResponseResult<T : Any> {
    data class Success<T : LocalEntity>(val data: T) : ResponseResult<LocalEntity>()
    class Error(val exception: Exception):ResponseResult<Exception>()
    object Loading:ResponseResult<Nothing>()
}