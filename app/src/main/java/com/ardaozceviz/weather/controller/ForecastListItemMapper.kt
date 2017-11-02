package com.ardaozceviz.weather.controller

import java.text.SimpleDateFormat

/**
 * Created by arda on 03/11/2017.
 */
class ForecastListItemMapper {
    companion object {
        fun getListItemTemperature(temp: Double): String {
            val temperature = temp - 273.15
            return "%.0f".format(temperature) + "°C"
        }

        fun getListItemDay(date: Int): String{
            val time = java.util.Date(date.toLong() * 1000)
            val sdf = SimpleDateFormat("EE")
            return sdf.format(time).toUpperCase()

        }

        fun getListItemIcon(condition: Int): String {
            return when (condition) {
                in 0..299 -> "storm"
                in 300..599 -> "rain"
                in 600..700 -> "snow"
                in 701..771 -> "fog"
                in 772..799 -> "tornado"
                800 -> "sunny"
                in 801..804 -> "cloud"
                904 -> "sunny"
                in 905..1000 -> "tornado"
                else -> "dunno"
            }
        }
    }

}