package com.ardaozceviz.weather.controller

import com.ardaozceviz.weather.model.WeatherDataModel

/**
 * Created by arda on 30/10/2017.
 */

class WeatherDataMapper(weatherDataModel: WeatherDataModel) {

    var tempString: String = "NA"
    var location: String = "NA"
    var iconName: String = ""

    private val temperature = weatherDataModel.main?.temp?.minus(273.15)
    private val condition : Int? = weatherDataModel.weather?.get(0)?.id

    init {
        tempString = temperature?.let { Math.rint(it) }?.toInt().toString()
        tempString = "$tempString°C"
        location = weatherDataModel.name
        if (condition!= null) iconName =  updateWeatherIcon(condition)
    }

    private fun updateWeatherIcon(condition: Int): String {

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