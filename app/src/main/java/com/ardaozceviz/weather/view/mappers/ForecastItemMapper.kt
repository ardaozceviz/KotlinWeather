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

    private val fahrenheit = forecast.apparentTemperatureHigh

    init {
        celsiusTemperature = ForecastCommonMapper.fahrenheitToCelsius(fahrenheit)
        dateTimeString = ForecastCommonMapper.unixToDate(forecast.time.toLong())
        iconName = ForecastCommonMapper.dayConditionToIcon(forecast.icon)
        weatherDescription = forecast.summary
        wind = ForecastCommonMapper.calculateWind(forecast.windSpeed)

        /*
        dateTimeString = ForecastCommonMapper.unixToDate(listItem.dt.toLong())
        val tmpWeatherDescription = listItem.weather?.get(0)?.description?.capitalize()
        val tmpWind = listItem.wind?.speed?.times(3.6)

        if (listItem.main != null) temperature = ForecastCommonMapper.calculateTemperature(listItem.main.temp)
        if (condition != null) iconName = ForecastCommonMapper.dayConditionToIcon(condition)
        if (tmpWeatherDescription != null) weatherDescription = tmpWeatherDescription
        wind = "%.2f".format(tmpWind) + " km/h"

        Log.d("ForecastItemMapper", "${listItem.dt}")
        Log.d("ForecastItemMapper", "${Date(listItem.dt.toLong()).time}")*/
    }
}