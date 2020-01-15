package com.goryachok.forecastapp.api

import com.goryachok.forecastapp.api.OpenWeatherMapAPI.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientBuilder {

    private val interceptor = Interceptor { chain ->
        val url =
            chain.request()
                .url
                .newBuilder()
                .addQueryParameter("appid", API_KEY)
                .addQueryParameter("units", "metric")
                .build()

        val request = chain.request().newBuilder().url(url).build()

        return@Interceptor chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()

    private val converterFactory = GsonConverterFactory.create()

    private val retrofit = Retrofit
        .Builder()
        .client(okHttpClient)
        .baseUrl(OpenWeatherMapAPI.DOMAIN)
        .addConverterFactory(converterFactory)
        .build()


    fun build(): OpenWeatherMapAPI {
        return retrofit.create(OpenWeatherMapAPI::class.java)
    }
}