package com.goryachok.forecastapp.di.api

import com.goryachok.forecastapp.BuildConfig
import com.goryachok.forecastapp.api.WeatherApiService
import com.goryachok.forecastapp.model.domain.RemoteEntity
import com.goryachok.forecastapp.network.LiveDataCallAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    fun provideRetrofit(client: OkHttpClient,factory:GsonConverterFactory):Retrofit{
        return Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapter.Factory())
            .addConverterFactory(factory)
            .baseUrl(BuildConfig.DOMAIN)
            .build()
    }

    @Provides
    fun provideConverterFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideClient(interceptor: Interceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideInterceptor():Interceptor{
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

    @Provides
    fun provideApi(retrofit: Retrofit):WeatherApiService{
        return retrofit.create(WeatherApiService::class.java)
    }
}