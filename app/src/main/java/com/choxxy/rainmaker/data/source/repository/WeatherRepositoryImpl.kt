package com.choxxy.rainmaker.data.source.repository

import com.choxxy.rainmaker.data.Resource
import com.choxxy.rainmaker.data.model.CurrentWeather
import com.choxxy.rainmaker.data.model.LocationModel
import com.choxxy.rainmaker.data.performGetOperation
import com.choxxy.rainmaker.data.source.remote.WeatherRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
 //   private val localDataSource: WeatherDao
) : WeatherRepository {

    override fun getWeather(location: LocationModel, refresh: Boolean):
        Flow<Resource<CurrentWeather?>> {
        return performGetOperation(
            networkCall = { remoteDataSource.getWeather(location) },
        )
    }

    /*

    override suspend fun getForecastWeather(
        cityId: Int,
        refresh: Boolean
    ): Flow<Resource<WeatherForecast>> {
            val mapper = WeatherMapper()
            return  performGetOperations(
                databaseQuery = { localDataSource. },
                networkCall = { remoteDataSource.getWeatherForecast(cityId) },
                saveCallResult = { localDataSource.insertWeather(mapper.transformToDomain(it!!)) },
                refresh
            )
        }

        withContext(ioDispatcher) {
        if (refresh) {
            val mapper = WeatherForecastMapperRemote()
            when (val response = remoteDataSource.getWeatherForecast(cityId)) {
                is Result.Success -> {
                    if (response.data != null) {
                        Result.Success(mapper.transformToDomain(response.data))
                    } else {
                        Result.Success(null)
                    }
                }

                is Result.Error -> {
                    Result.Error(response.exception)
                }

                else -> Result.Loading
            }
        } else {
            val mapper = WeatherForecastMapperLocal()
            val forecast = localDataSource.getForecastWeather()
            if (forecast != null) {
                Result.Success(mapper.transformToDomain(forecast))
            } else {
                Result.Success(null)
            }
        }
    }

    override suspend fun storeWeatherData(weather: Weather) = withContext(ioDispatcher) {
        val mapper = WeatherMapperLocal()
        localDataSource.saveWeather(mapper.transformToDto(weather))
    }

    override suspend fun storeForecastData(forecasts: List<WeatherForecast>) =
        withContext(ioDispatcher) {
            val mapper = WeatherForecastMapperLocal()
            mapper.transformToDto(forecasts).let { listOfDbForecast ->
                listOfDbForecast.forEach {
                    localDataSource.saveForecastWeather(it)
                }
            }
        }

    override suspend fun getSearchWeather(location: String): Result<Weather?> =
        withContext(ioDispatcher) {
            val mapper = WeatherMapperRemote()
            return@withContext when (val response = remoteDataSource.getSearchWeather(location)) {
                is Result.Success -> {
                    if (response.data != null) {
                        Result.Success(mapper.transformToDomain(response.data))
                    } else {
                        Result.Success(null)
                    }
                }
                is Result.Error -> {
                    Result.Error(response.exception)
                }
                else -> {
                    Result.Loading
                }
            }
        }

    override suspend fun deleteWeatherData() = withContext(ioDispatcher) {
        localDataSource.deleteWeather()
    }

    override suspend fun deleteForecastData() {
        localDataSource.deleteForecastWeather()
    }
   
     */
}
