package com.ardaozceviz.weather.view.mappers

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by arda on 07/11/2017.
 */
object ForecastCommonMapper {

    fun calculateTemperature(temp: Double): String {
        val temperature = temp - 273.15
        return "%.0f".format(temperature) + "°C"
    }

    fun calculateWind(w: Double?): String {
        return if (w != null) {
            val wind = w.times(3.6)
            "%.2f".format(wind) + " km/h"
        } else {
            "NA"
        }

    }

    fun fahrenheitToCelsius(f: Double?): String {
        return if (f != null) {
            val temperature = (f - 32) * 0.5556
            "%.0f".format(temperature) + "°C"
        } else {
            "NA"
        }
    }

    fun getListItemDay(date: Int): String {
        val time = java.util.Date(date.toLong() * 1000)
        val sdf = SimpleDateFormat("EE")
        return sdf.format(time).toUpperCase()

    }

    fun dayConditionToIcon(condition: String?): String {
        return when (condition) {
            "clear-day", "clear-night" -> "day_clear_sky"
            "rain" -> "day_rain"
            "snow" -> "day_snow"
            "sleet" -> "day_sleet"
            "wind" -> "day_night_breeze"
            "fog" -> "day_fog"
            "cloudy" -> "day_few_clouds"
            "partly-cloudy-day", "partly-cloudy-night" -> "day_night_smoke"
            "hail" -> "day_hail"
            "thunderstorm" -> "day_thunderstorm"
            "tornado" -> "day_night_tornado"
            else -> "day_clear_sky"
        }
    }

    fun nightConditionToIcon(condition: String?): String {
        return when (condition) {
            "clear-day", "clear-night" -> "night_clear_sky"
            "rain" -> "night_rain"
            "snow" -> "night_snow"
            "sleet" -> "night_sleet"
            "wind" -> "day_night_breeze"
            "fog" -> "night_fog"
            "cloudy" -> "night_few_clouds"
            "partly-cloudy-day", "partly-cloudy-night" -> "day_night_smoke"
            "hail" -> "night_hail"
            "thunderstorm" -> "night_thunderstorm"
            "tornado" -> "day_night_tornado"
            else -> "night_clear_sky"
        }
    }

    fun unixToDate(unixTime: Long): String {
        val date = Date(unixTime * 1000L) // *1000 is to convert seconds to milliseconds
        val sdf = SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault())
        return sdf.format(date)
    }

}