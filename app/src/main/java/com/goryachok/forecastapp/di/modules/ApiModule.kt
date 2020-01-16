package com.goryachok.forecastapp.di.modules

import android.util.Log
import com.goryachok.forecastapp.api.OpenWeatherMapAPI
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    fun provideApiClient(retrofit: Retrofit): OpenWeatherMapAPI {
        Log.d("DaggerDebug", OpenWeatherMapAPI::class.java.name)
        return retrofit.create(OpenWeatherMapAPI::class.java)
    }

    @Provides
    fun provideInterceptor() = Interceptor { chain ->
        val url =
            chain.request()
                .url
                .newBuilder()
                .addQueryParameter("appid", OpenWeatherMapAPI.API_KEY)
                .addQueryParameter("units", "metric")
                .build()

        val request = chain.request().newBuilder().url(url).build()
        Log.d("DaggerDebug", Interceptor::class.java.name)

        return@Interceptor chain.proceed(request)
    }

    @Provides
    fun provideOkHttp(interceptor: Interceptor): OkHttpClient {
        Log.d("DaggerDebug", OkHttpClient::javaClass.name)
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideGsonConcerterFactory(): GsonConverterFactory {
        Log.d("DaggerDebug", GsonConverterFactory::class.java.name)
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        Log.d("DaggerDebug", Retrofit::class.java.name)
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(OpenWeatherMapAPI.DOMAIN)
            .addConverterFactory(converterFactory)
            .build()
    }
}