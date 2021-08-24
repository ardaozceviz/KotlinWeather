package com.mayokunadeniyi.instantweather.data.source.remote

import com.choxxy.rainmaker.data.model.LocationModel
import com.choxxy.rainmaker.data.model.NetworkWeather
import com.choxxy.rainmaker.data.model.NetworkWeatherForecast

/**
 * Created by Mayokun Adeniyi on 13/07/2020.
 */

interface WeatherRemoteDataSource {
    suspend fun getWeather(location: LocationModel): Result<NetworkWeather>

    suspend fun getWeatherForecast(cityId: Int): Result<List<NetworkWeatherForecast>>

    suspend fun getSearchWeather(query: String): Result<NetworkWeather>
}
