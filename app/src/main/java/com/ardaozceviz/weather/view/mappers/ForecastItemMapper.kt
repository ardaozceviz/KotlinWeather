package com.ardaozceviz.weather.view.mappers

import com.ardaozceviz.weather.model.Data

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
        dateTimeString = ForecastCommonMapper.unixToDate(forecast.time.toLong())
        iconName = ForecastCommonMapper.dayConditionToIcon(forecast.icon)
        weatherDescription = forecast.summary
        humidity = ForecastCommonMapper.calculateHumidity(forecast.humidity)
        wind = ForecastCommonMapper.calculateWind(forecast.windSpeed)
    }
}