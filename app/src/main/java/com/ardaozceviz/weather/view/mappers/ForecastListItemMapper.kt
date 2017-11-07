package com.ardaozceviz.weather.view.mappers

import java.text.SimpleDateFormat

/**
 * Created by arda on 07/11/2017.
 */
class ForecastListItemMapper {
    companion object {
        fun getListItemTemperature(temp: Double): String {
            val temperature = temp - 273.15
            return "%.0f".format(temperature) + "°C"
        }

        fun getListItemDay(date: Int): String {
            val time = java.util.Date(date.toLong() * 1000)
            val sdf = SimpleDateFormat("EE")
            return sdf.format(time).toUpperCase()

        }
    }

}