package com.choxxy.rainmaker.data.source.remote.retrofit

import com.choxxy.rainmaker.data.model.CurrentWeather
import com.choxxy.rainmaker.data.model.LocationResponse
import com.choxxy.rainmaker.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    /**
     * This function gets the [WeatherResponse] for the [location] the
     * user searched for.
     */
    // api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") location: String
    ): Response<CurrentWeather>

    // https://api.openweathermap.org/data/2.5/weather?id=2172797&appid=
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("id") cityId: Int
    ): Response<CurrentWeather>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): Response<CurrentWeather>

    // https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=hourly,minutely&appid=
    // This function gets the weather information for the user's location.
    @GET("data/2.5/onecall")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") excludes: String
    ): Response<WeatherResponse>

    // http://api.openweathermap.org/geo/1.0/reverse?lat=51.5098&lon=-0.1180&limit=1&appid=
    // This function gets the weather forecast information for the user's location.
    @GET("geo/1.0/reverse")
    suspend fun getLocationName(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int,
    ): Response<LocationResponse>
}
