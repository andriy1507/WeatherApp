package com.goryachok.forecastapp.network

import androidx.lifecycle.LiveData
import com.goryachok.forecastapp.model.domain.RemoteEntity
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class LiveDataCallAdapter<T:RemoteEntity>:CallAdapter<T,LiveData<T>> {

    class Factory: CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ): CallAdapter<*, *>? {
            TODO()
        }
    }

    override fun responseType(): Type {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun adapt(call: Call<T>): LiveData<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}