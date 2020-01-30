package com.goryachok.repository

import com.goryachok.local.model.LocalData

sealed class ResponseResult<T : Any> {
    data class Success<T : LocalData>(val data: T):ResponseResult<LocalData>()
    class Error(val exception: Exception):ResponseResult<Exception>()
    object Loading:ResponseResult<Nothing>()
}