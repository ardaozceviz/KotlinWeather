package com.choxxy.rainmaker.data.source.remote

import com.choxxy.rainmaker.data.Resource
import com.choxxy.rainmaker.data.model.CurrentWeather
import com.choxxy.rainmaker.data.model.LocationModel
import com.choxxy.rainmaker.data.source.remote.retrofit.WeatherApiService
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRemoteDataSource {
    override suspend fun getWeather(location: LocationModel): Resource<CurrentWeather?> {
        val response = apiService.getCurrentWeather(
            location.latitude, location.longitude
        )
        return if (response.isSuccessful) {
            val body = response.body()
            Resource.success(body)
        } else {
            Resource.error(response.message())
        }
    }
/*
    override suspend fun getWeatherForecast(cityId: Int): Resource<List<WeatherForecastResponse?>?> {
        val response = apiService.getWeatherForecast(cityId)
        return if (response.isSuccessful) {
            val body = response.body()
            Resource.success(body)
        } else {
            Resource.error(response.message())
        }
    }
    override suspend fun getSearchWeather(query: String): Resource<WeatherResponse?> {
        val response = apiService.getSpecificWeather(query)
        return if (response.isSuccessful) {
            val body = response.body()
            Resource.success(body)
        } else {
            Resource.error(response.message())
        }
    }*/
}
