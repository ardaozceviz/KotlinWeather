package com.ardaozceviz.weather.view.mappers

import android.util.Log
import com.ardaozceviz.weather.model.TAG_M_FORECAST
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
                "%.0f".format(temperature) + "°C"
            } else {
                "NA"
            }
        } else {
            if (f1 != null) {
                val temperatureLow = (f1 - 32) * 0.5556
                val temperatureHigh = (f2 - 32) * 0.5556
                "%.0f".format(temperatureLow) + "°" + "/" + "%.0f".format(temperatureHigh) + "°"
            } else {
                "NA"
            }
        }
    }

    fun getListItemDay(unixTime: Long): String {
        val date = Date(unixTime * 1000L) // *1000 is to convert seconds to milliseconds
        val sdf = SimpleDateFormat("EE", Locale.getDefault())
        return sdf.format(date).toUpperCase()

    }

    fun getIcon(condition: String?): String {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val isNight = hour < 6 || hour > 18
        return if (isNight) {
            Log.d(TAG_M_FORECAST, "isNight: $isNight")
            ForecastCommonMapper.nightConditionToIcon(condition)
        } else {
            Log.d(TAG_M_FORECAST, "isNight: $isNight")
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
            "cloudy" -> "day_few_clouds"
            "partly-cloudy-day", "partly-cloudy-night" -> "day_few_clouds"
            "hail" -> "day_hail"
            "thunderstorm" -> "day_thunderstorm"
            "tornado" -> "day_night_tornado"
            else -> "day_clear_sky"
        }
    }

    private fun nightConditionToIcon(condition: String?): String {
        return when (condition) {
            "clear-day", "clear-night" -> "night_clear_sky"
            "rain" -> "night_rain"
            "snow" -> "night_snow"
            "sleet" -> "night_sleet"
            "wind" -> "day_night_breeze"
            "fog" -> "night_fog"
            "cloudy" -> "night_few_clouds"
            "partly-cloudy-day", "partly-cloudy-night" -> "night_few_clouds"
            "hail" -> "night_hail"
            "thunderstorm" -> "night_thunderstorm"
            "tornado" -> "day_night_tornado"
            else -> "night_clear_sky"
        }
    }

    fun unixToDate(unixTime: Long): String {
        val date = Date(unixTime * 1000L) // *1000 is to convert seconds to milliseconds
        //val sdf = SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault())
        val sdf = SimpleDateFormat("E, MMM dd", Locale.getDefault())
        return sdf.format(date)
    }

}