package com.mayokunadeniyi.instantweather.data.source.repository

import com.choxxy.rainmaker.data.model.LocationModel
import com.choxxy.rainmaker.data.model.Weather
import com.choxxy.rainmaker.data.model.WeatherForecast

/**
 * Created by Mayokun Adeniyi on 13/07/2020.
 */
interface WeatherRepository {

    suspend fun getWeather(location: LocationModel, refresh: Boolean): Result<Weather?>

    suspend fun getForecastWeather(cityId: Int, refresh: Boolean): Result<List<WeatherForecast>?>

    suspend fun getSearchWeather(location: String): Result<Weather?>

    suspend fun storeWeatherData(weather: Weather)

    suspend fun storeForecastData(forecasts: List<WeatherForecast>)

    suspend fun deleteWeatherData()

    suspend fun deleteForecastData()
}
