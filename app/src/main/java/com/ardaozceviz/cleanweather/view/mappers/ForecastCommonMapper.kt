package com.ardaozceviz.cleanweather.view.mappers

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by arda on 07/11/2017.
 */
object ForecastCommonMapper {

    fun getWeatherDescription(icon: String): String = when (icon) {
        "clear-day", "clear-night" -> "Clear sky"
        "rain" -> "Rain"
        "snow" -> "Snow"
        "sleet" -> "Sleet"
        "wind" -> "Windy"
        "fog" -> "Fog"
        "cloudy" -> "Cloudy"
        "partly-cloudy-day", "partly-cloudy-night" -> "Partly cloudy"
        "hail" -> "Hail"
        "thunderstorm" -> "Thunderstorms"
        "tornado" -> "Tornado"
        else -> "NA"
    }

    // This is for km/h
    /*fun calculateWind(w: Double?): String {
        return if (w != null) {
            val wind = w.times(3.6)
            "%.2f".format(wind) + " km/h"
        } else {
            "NA"
        }
    }*/

    fun calculateWind(w: Double?): String {
        return if (w != null) {
            "%.2f".format(w) + " m/s"
        } else {
            "NA"
        }
    }

    fun calculateHumidity(h: Double?): String {
        return if (h != null) {
            val humidity = h.times(100)
            "%.0f".format(humidity) + " %"
        } else {
            "NA"
        }
    }

    // f1 = low, f2 = high
    fun fahrenheitToCelsius(f1: Double?, f2: Double? = null): String {
        return if (f2 == null) {
            if (f1 != null) {
                val temperature = (f1 - 32) * 0.5556
                if ("%.0f".format(temperature) == "-0") {
                    "0°C"
                } else {
                    "%.0f".format(temperature) + "°C"
                }
            } else {
                "NA"
            }
        } else {
            if (f1 != null) {
                val temperatureLow = (f1 - 32) * 0.5556
                val temperatureHigh = (f2 - 32) * 0.5556
                if ("%.0f".format(temperatureLow) == "-0") {
                    "0°" + " | " + "%.0f".format(temperatureHigh) + "°"
                } else if ("%.0f".format(temperatureHigh) == "-0") {
                    "%.0f".format(temperatureLow) + "°" + " | " + "0°"
                } else if ("%.0f".format(temperatureLow) == "-0" && "%.0f".format(temperatureHigh) == "-0") {
                    "0°" + " | " + "0°"
                } else {
                    "%.0f".format(temperatureLow) + "°" + " | " + "%.0f".format(temperatureHigh) + "°"
                }
            } else {
                "NA"
            }
        }
    }

    fun getListItemDay(unixTime: Long): String {
        val date = Date(unixTime * 1000L) // *1000 is to convert seconds to milliseconds
        val sdf = SimpleDateFormat("E", Locale.getDefault())
        return sdf.format(date)

    }

    fun getIcon(condition: String?, timeStamp: Long? = null): String {
        val cal = Calendar.getInstance()
        var hour = cal.get(Calendar.HOUR_OF_DAY)
        if (timeStamp != null) {
            val date = Date(timeStamp * 1000L)
            val sdf = SimpleDateFormat("H", Locale.getDefault())
            hour = sdf.format(date).toInt()
        }
        val isNight = hour < 6 || hour > 18
        return if (isNight) {
            ForecastCommonMapper.nightConditionToIcon(condition)
        } else {
            ForecastCommonMapper.dayConditionToIcon(condition)
        }
    }

    fun dayConditionToIcon(condition: String?): String {
        return when (condition) {
            "clear-day", "clear-night" -> "day_clear_sky"
            "rain" -> "day_rain"
            "snow" -> "day_snow"
            "sleet" -> "day_sleet"
            "wind" -> "day_night_breeze"
            "fog" -> "day_fog"
            "cloudy" -> "day_clouds"
            "partly-cloudy-day", "partly-cloudy-night" -> "day_few_clouds"
            "hail" -> "day_hail"
            "thunderstorm" -> "day_thunderstorm"
            "tornado" -> "day_night_tornado"
            else -> "day_clear_sky"
        }
    }

    private fun nightConditionToIcon(condition: String?): String = when (condition) {
        "clear-day", "clear-night" -> "night_clear_sky"
        "rain" -> "night_rain"
        "snow" -> "night_snow"
        "sleet" -> "night_sleet"
        "wind" -> "day_night_breeze"
        "fog" -> "night_fog"
        "cloudy" -> "night_clouds"
        "partly-cloudy-night", "partly-cloudy-day" -> "night_few_clouds"
        "hail" -> "night_hail"
        "thunderstorm" -> "night_thunderstorm"
        "tornado" -> "day_night_tornado"
        else -> "night_clear_sky"
    }

    fun timestampToDate(timeStamp: Long): String {
        val date = Date(timeStamp * 1000L) // *1000 is to convert seconds to milliseconds
        //val sdf = SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault())
        val sdf = SimpleDateFormat("E, MMM dd", Locale.getDefault())
        return sdf.format(date)
    }

    fun timestampToHour(timeStamp: Long): String {
        val date = Date(timeStamp * 1000L)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date).toLowerCase()
    }

}