package com.ardaozceviz.cleanweather.view.mappers

import com.ardaozceviz.cleanweather.model.Data

/**
 * Created by arda on 07/11/2017.
 */

class ForecastItemMapper(forecast: Data) {

    var weatherDescription = "NA"
    var iconName = ""
    var celsiusTemperature = "NA"
    var humidity = "NA"
    var wind = "NA"
    var dateTimeString = "NA"

    private val fahrenheitLow = forecast.apparentTemperatureLow
    private val fahrenheitHigh = forecast.apparentTemperatureHigh
    private val fahrenheit = (fahrenheitLow + fahrenheitHigh) / 2

    init {
        celsiusTemperature = ForecastCommonMapper.fahrenheitToCelsius(fahrenheit)
        dateTimeString = ForecastCommonMapper.timestampToDate(forecast.time.toLong())
        iconName = ForecastCommonMapper.dayConditionToIcon(forecast.icon)
        weatherDescription = ForecastCommonMapper.getWeatherDescription(forecast.icon)
        humidity = ForecastCommonMapper.calculateHumidity(forecast.humidity)
        wind = ForecastCommonMapper.calculateWind(forecast.windSpeed)
    }
}