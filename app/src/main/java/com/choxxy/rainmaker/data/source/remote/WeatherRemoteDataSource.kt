package com.choxxy.rainmaker.data.source.remote

import com.choxxy.rainmaker.data.Resource
import com.choxxy.rainmaker.data.model.CurrentWeather
import com.choxxy.rainmaker.data.model.LocationModel

interface WeatherRemoteDataSource {
    suspend fun getWeather(location: LocationModel): Resource<CurrentWeather?>

    // suspend fun getWeatherForecast(cityId: Int): Resource<List<WeatherForecastResponse?>?>

    // suspend fun getSearchWeather(query: String): Resource<WeatherResponse?>
}
