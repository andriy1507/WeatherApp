package com.goryachok.forecastapp.api

import com.goryachok.forecastapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientProvider {
    fun create(): WeatherApiService {
        val interceptor = provideInterceptor()
        val okhttp = provideClient(interceptor)
        val gson = provideConverterFactory()
        val retrofit = provideRetrofit(okhttp, gson)
        return provideApi(retrofit)
    }

    private fun provideRetrofit(client: OkHttpClient, factory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(factory)
            .baseUrl(BuildConfig.DOMAIN)
            .build()
    }

    private fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    private fun provideClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    private fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url =
                chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("appid", BuildConfig.API_KEY)
                    .addQueryParameter("units", "metric")
                    .build()
            val request = chain.request().newBuilder().url(url).build()
            return@Interceptor chain.proceed(request)
        }
    }

    private fun provideApi(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }
}