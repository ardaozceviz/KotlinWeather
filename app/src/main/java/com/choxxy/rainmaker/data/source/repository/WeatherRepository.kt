package com.choxxy.rainmaker.data.source.repository

import com.choxxy.rainmaker.data.Resource
import com.choxxy.rainmaker.data.model.CurrentWeather
import com.choxxy.rainmaker.data.model.LocationModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeather(location: LocationModel, refresh: Boolean): Flow<Resource<CurrentWeather?>>

    /*
    suspend fun getForecastWeather(cityId: Int, refresh: Boolean): Result<List<WeatherForecast>?>

    suspend fun getSearchWeather(location: String): Result<Weather?>

    suspend fun storeWeatherData(weather: Weather)

    suspend fun storeForecastData(forecasts: List<WeatherForecast>)

    suspend fun deleteWeatherData()

    suspend fun deleteForecastData()*/
}
