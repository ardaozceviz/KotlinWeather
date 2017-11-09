package com.ardaozceviz.weather.view.mappers

import android.util.Log
import com.ardaozceviz.weather.model.ForecastDataModel
import com.ardaozceviz.weather.model.TAG_M_FORECAST
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by arda on 07/11/2017.
 */

class ForecastDataMapper(forecastDataModel: ForecastDataModel) {
    var location = "NA"
    var celsiusTemperature = "NA"
    var iconName: String = ""
    var weatherDescription = ""
    var currentDateTimeString = ""
    var wind = ""

    private var isNight = false
    private val simpleDateFormatDate = SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault())
    private val cal = Calendar.getInstance()
    private val hour = cal.get(Calendar.HOUR_OF_DAY)

    init {
        val fahrenheit = forecastDataModel.currently.apparentTemperature
        val condition = forecastDataModel.currently.icon

        currentDateTimeString = simpleDateFormatDate.format(Date().time)
        location = forecastDataModel.timezone
        isNight = hour < 6 || hour > 18
        celsiusTemperature = ForecastCommonMapper.fahrenheitToCelsius(fahrenheit)
        iconName = if (isNight) {
            Log.d(TAG_M_FORECAST, "isNight: $isNight")
            ForecastCommonMapper.nightConditionToIcon(condition)
        } else {
            Log.d(TAG_M_FORECAST, "isNight: $isNight")
            ForecastCommonMapper.dayConditionToIcon(condition)
        }
        weatherDescription = forecastDataModel.currently.summary
        wind = ForecastCommonMapper.calculateWind(forecastDataModel.currently.windSpeed)


    }
}