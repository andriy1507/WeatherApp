package com.goryachok.forecastapp.network.api


import com.goryachok.forecastapp.model.ForecastResponse
import com.goryachok.forecastapp.model.WeatherResponse
import com.goryachok.forecastapp.network.connectivity.ConnectivityInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherMapAPI {

    companion object {

        private const val API_KEY = "f19f55d718abb04df88d9190337bd5b9"

        private const val DOMAIN = "https://api.openweathermap.org/"

        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): OpenWeatherMapAPI {

            val requestInterceptor = Interceptor { chain ->

                val url =
                    chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("appid", API_KEY)
                        .addQueryParameter("units","metric")
                        .build()

                val request = chain.request().newBuilder().url(url).build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }
    }

    @GET("data/2.5/weather")
    suspend fun getWeatherByCityAsync(
        @Query("q") city: String): WeatherResponse

    @GET("data/2.5/weather")
    suspend fun getWeatherByCoordinatesAsync(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float): WeatherResponse

    @GET("data/2.5/forecast")
    suspend fun getForecastByCityAsync(
        @Query("q") city: String): ForecastResponse

    @GET("data/2.5/forecast")
    suspend fun getForecastByCoordinatesAsync(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float): ForecastResponse
}
