package com.ardaozceviz.cleanweather.view.mappers

import com.ardaozceviz.cleanweather.model.ForecastDataModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by arda on 07/11/2017.
 */

class ForecastDataMapper(forecastDataModel: ForecastDataModel) {
    var location = "NA"
    var celsiusTemperature = "NA"
    var iconName: String = ""
    var weatherDescription = "NA"
    var currentDateTimeString = "NA"
    var humidity = "NA"
    var wind = "NA"

    init {
        val simpleDateFormatDate = SimpleDateFormat("E, MMM dd - HH:mm", Locale.getDefault())
        val fahrenheit = forecastDataModel.currently.temperature
        val icon = forecastDataModel.currently.icon

        currentDateTimeString = simpleDateFormatDate.format(Date().time)
        location = forecastDataModel.timezone
        celsiusTemperature = ForecastCommonMapper.fahrenheitToCelsius(fahrenheit)
        iconName = ForecastCommonMapper.getIcon(icon)
        weatherDescription = ForecastCommonMapper.getWeatherDescription(icon)
        humidity = ForecastCommonMapper.calculateHumidity(forecastDataModel.currently.humidity)
        wind = ForecastCommonMapper.calculateWind(forecastDataModel.currently.windSpeed)
    }
}