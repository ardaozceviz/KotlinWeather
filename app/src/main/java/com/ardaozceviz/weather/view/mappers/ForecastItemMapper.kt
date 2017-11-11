package com.ardaozceviz.weather.view.mappers

import com.ardaozceviz.weather.model.Data

/**
 * Created by arda on 07/11/2017.
 */

class ForecastItemMapper(forecast: Data) {

    var weatherDescription = ""
    var iconName = ""
    var celsiusTemperature = "NA"
    var wind = ""
    var dateTimeString = ""

    private val fahrenheitLow = forecast.apparentTemperatureLow
    private val fahrenheitHigh = forecast.apparentTemperatureHigh
    private val fahrenheit = (fahrenheitLow + fahrenheitHigh) / 2

    init {
        celsiusTemperature = ForecastCommonMapper.fahrenheitToCelsius(fahrenheit)
        dateTimeString = ForecastCommonMapper.unixToDate(forecast.time.toLong())
        iconName = ForecastCommonMapper.dayConditionToIcon(forecast.icon)
        weatherDescription = forecast.summary
        wind = ForecastCommonMapper.calculateWind(forecast.windSpeed)
    }
}